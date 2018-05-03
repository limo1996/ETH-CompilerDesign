package cd.backend.codegen;

import static cd.Config.SCANF;
import static cd.backend.codegen.AssemblyEmitter.constant;
import static cd.backend.codegen.RegisterManager.STACK_REG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cd.Config;
import cd.ToDoException;
import cd.backend.ExitCode;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast;
import cd.ir.Ast.BinaryOp;
import cd.ir.Ast.BooleanConst;
import cd.ir.Ast.BuiltInRead;
import cd.ir.Ast.Cast;
import cd.ir.Ast.Expr;
import cd.ir.Ast.Field;
import cd.ir.Ast.Index;
import cd.ir.Ast.IntConst;
import cd.ir.Ast.MethodCallExpr;
import cd.ir.Ast.NewArray;
import cd.ir.Ast.NewObject;
import cd.ir.Ast.NullConst;
import cd.ir.Ast.ThisRef;
import cd.ir.Ast.UnaryOp;
import cd.ir.Ast.Var;
import cd.ir.ExprVisitor;
import cd.ir.Symbol.ArrayTypeSymbol;
import cd.ir.Symbol.ClassSymbol;
import cd.ir.Symbol.TypeSymbol;
import cd.util.debug.AstOneLine;

/**
 * Generates code to evaluate expressions. After emitting the code, returns a
 * String which indicates the register where the result can be found.
 */
class ExprGenerator extends ExprVisitor<Register, Context> {
	protected final AstCodeGenerator cg;

	ExprGenerator(AstCodeGenerator astCodeGenerator) {
		cg = astCodeGenerator;
	}

	public Register gen(Expr ast) {
		return visit(ast, null);
	}

	@Override
	public Register visit(Expr ast, Context arg) {
		try {
			cg.emit.increaseIndent("Emitting " + AstOneLine.toString(ast));
			return super.visit(ast, arg);
		} finally {
			cg.emit.decreaseIndent();
		}

	}
	
	@Override
	public Register binaryOp(BinaryOp ast, Context arg) {
		//arg.returnValue = true;
		boolean store;
		Register leftReg, rightReg;
		
		int leftRN = cg.rnv.calc(ast.left());
		int rightRN = cg.rnv.calc(ast.right());
		int available = cg.rm.availableRegisters();
		
		if (leftRN > rightRN) {
			
			/*
			 * Determine whether the intermediate result will have to be stored
			 * on the stack because the test of the computation uses all registers.
			 */
			store = rightRN >= available;
			
			/*
			 * Generate code for the LHS expression.
			 */
			leftReg = visit(ast.left(), arg);
			
			/*
			 * Store LHS on the stack if needed.
			 */
			if(store) {
				cg.emit.emit("pushl", leftReg);
				cg.rm.releaseRegister(leftReg);
			}
			
			/*
			 * Generate code for the RHS expression.
			 */
			rightReg = visit(ast.right(), arg);
			
			/*
			 *  Reload LHS from the stack if needed.
			 */
			if(store) {
				leftReg = cg.rm.getRegister();
				cg.emit.emit("popl", leftReg);
			}
			
		} else {
			
			/*
			 * Determine whether the intermediate result will have to be stored
			 * on the stack because the test of the computation uses all registers.
			 */
			store = leftRN >= available;

			/*
			 * Generate code for the RHS expression.
			 */
			rightReg = visit(ast.right(), arg);
			
			/*
			 * Store RHS on the stack if needed.
			 */
			if(store) {
				cg.emit.emit("pushl", rightReg);
				cg.rm.releaseRegister(rightReg);
			}
			
			/*
			 * Generate code for the LHS expression.
			 */
			leftReg = visit(ast.left(), arg);
			
			/*
			 *  Reload RHS from the stack if needed.
			 */
			if(store) {
				rightReg = cg.rm.getRegister();
				cg.emit.emit("popl", rightReg);
			}
		}

		cg.debug("Binary Op: %s (%s,%s)", ast, leftReg, rightReg);

		switch (ast.operator) {
		case B_TIMES:
			cg.emit.emit("imul", rightReg, leftReg);
			break;
		case B_PLUS:
			cg.emit.emit("add", rightReg, leftReg);
			break;
		case B_MINUS:
			cg.emit.emit("sub", rightReg, leftReg);
			break;
		case B_MOD:
			/*
			 * Fallthrough
			 */
		case B_DIV:
			
			/*
			 * Test whether rightReg holds 0 and exit with code 7 if so.
			 */
			String legalLabel = cg.emit.uniqueLabel();
			cg.emit.emit("cmpl", AssemblyEmitter.constant(0), rightReg);
			cg.emit.emit("jne", legalLabel);
			BackendUtils.emitExit(cg.emit, ExitCode.DIVISION_BY_ZERO, arg.SP_offset);
			cg.emit.emitLabel(legalLabel);
			
			
			// Save EAX, EBX, and EDX to the stack if they are not used
			// in this subtree (but are used elsewhere). We will be
			// changing them.
			List<Register> dontBother = Arrays.asList(rightReg, leftReg);
			Register[] affected = { Register.EAX, Register.EBX, Register.EDX };

			for (Register s : affected)
				if (!dontBother.contains(s) && cg.rm.isInUse(s))
					cg.emit.emit("pushl", s);

			// Move the LHS (numerator) into eax
			// Move the RHS (denominator) into ebx
			cg.emit.emit("pushl", rightReg);
			cg.emit.emit("pushl", leftReg);
			cg.emit.emit("popl", Register.EAX);
			cg.emit.emit("popl", "%ebx");
			cg.emit.emitRaw("cltd"); // sign-extend %eax into %edx
			cg.emit.emit("idivl", "%ebx"); // division, result into edx:eax

			// Move the result into the LHS, and pop off anything we saved
			if(ast.operator.equals(Ast.BinaryOp.BOp.B_DIV))
				cg.emit.emitMove(Register.EAX, leftReg);
			else
				cg.emit.emitMove(Register.EDX, leftReg);
			for (int i = affected.length - 1; i >= 0; i--) {
				Register s = affected[i];
				if (!dontBother.contains(s) && cg.rm.isInUse(s))
					cg.emit.emit("popl", s);
			}
			break;
			
		case B_AND:
			cg.emit.emit("andl", rightReg, leftReg);
			break;
			
		case B_OR:
			cg.emit.emit("orl", rightReg, leftReg);
			break;
			
		case B_EQUAL:
			cg.emit.relationalOperator("je", leftReg, rightReg);
			break;
			
		case B_NOT_EQUAL:
			cg.emit.relationalOperator("jne", leftReg, rightReg);
			break;
			
		case B_LESS_THAN:
			cg.emit.relationalOperator("jl", leftReg, rightReg);
			break;
			
		case B_LESS_OR_EQUAL:
			cg.emit.relationalOperator("jle", leftReg, rightReg);
			break;
			
		case B_GREATER_THAN:
			cg.emit.relationalOperator("jg", leftReg, rightReg);
			break;
			
		case B_GREATER_OR_EQUAL:
			cg.emit.relationalOperator("jge", leftReg, rightReg);
			break;
		}

		cg.rm.releaseRegister(rightReg);

		return leftReg;
	}

	@Override
	public Register booleanConst(BooleanConst ast, Context arg) {
		
		//cg.emit.emitConstantData(data);
		
		/*
		 * Get a free register and set it to 0 (for false) or 1 (for true).
		 */
		Register reg = cg.rm.getRegister();
		if(ast.value)
			cg.emit.emitMove(AssemblyEmitter.constant(1), reg);
		else
			cg.emit.emitMove(AssemblyEmitter.constant(0), reg);
		
		return reg;
	}

	@Override
	public Register builtInRead(BuiltInRead ast, Context arg) {
		Register reg = cg.rm.getRegister();
		int offset = BackendUtils.allocSpaceSize(arg.SP_offset, 2);
		cg.emit.emit("subl", constant(offset), STACK_REG);
		cg.emit.emit("leal", AssemblyEmitter.registerOffset(8, STACK_REG), reg);
		cg.emit.emitStore(reg, 4, STACK_REG);
		cg.emit.emitStore("$STR_D", 0, STACK_REG);
		cg.emit.emit("call", SCANF);
		cg.emit.emitLoad(8, STACK_REG, reg);
		cg.emit.emit("addl", constant(offset), STACK_REG);
		return reg;
	}

	@Override
	public Register cast(Cast ast, Context arg) {
		Register obj = visit(ast.arg(), arg);

		// check for null pointer -> We do not exit but pass it along
		String endLabel = cg.emit.uniqueLabel();
        cg.emit.emit("cmp", constant(0), obj);
        cg.emit.emit("je", endLabel);
        //BackendUtils.emitExit(cg.emit, ExitCode.NULL_POINTER, arg.SP_offset);
        //cg.emit.emitLabel(okLabel);
        
        // load cast-vtable
		String v_table_label = BackendUtils.getVTableLabel(ast.typeName);
		TypeSymbol typeSymbol = ast.type;
		if (typeSymbol instanceof ArrayTypeSymbol) {
			v_table_label = BackendUtils.getVTableArrayLabel(ast.typeName);
		} 
		
		// load v_table into 
		Register v_table = cg.rm.getRegister();
        cg.emit.emit("leal", v_table_label, v_table);
        
        Register o_table = cg.rm.getRegister();
		cg.emit.emitLoad(0, obj, o_table); // leal

		String loopLabel = cg.emit.uniqueLabel();
		cg.emit.emitLabel(loopLabel);

		// check equality
		cg.emit.emit("cmp", o_table, v_table);
		cg.emit.emit("je", endLabel);

		// fail, if no parent
		cg.emit.emit("cmpl", constant(0), AssemblyEmitter.registerOffset(0, o_table));
		String errLabel = cg.emit.uniqueLabel();
		cg.emit.emit("je", errLabel);

		// load parent vtable
		cg.emit.emitLoad(0, o_table, v_table);
		cg.emit.emit("jmp", loopLabel);

		// err in downcast
		cg.emit.emitLabel(errLabel);
		BackendUtils.emitExit(cg.emit, ExitCode.INVALID_DOWNCAST, arg.SP_offset);

        // release regs
		cg.rm.releaseRegister(v_table);
        cg.rm.releaseRegister(o_table);
             
        cg.emit.emitLabel(endLabel);
		return obj;
	}

	@Override
	public Register index(Index ast, Context arg) {
		boolean return_value = arg.returnValue;
		arg.returnValue = true;
		
		boolean store;
		Register leftReg, rightReg;
		int leftRN = cg.rnv.calc(ast.left());
		int rightRN = cg.rnv.calc(ast.right());
		int available = cg.rm.availableRegisters();
		
		if(leftRN > rightRN) {
			
			store = rightRN >= available;
			
			// Generate code for the array to be indexed.
			leftReg = visit(ast.left(), arg);
			
			// Store the computed value if needed.
			if(store) {
				cg.emit.emit("pushl", leftReg);
				cg.rm.releaseRegister(leftReg);
			}
			
			// Generate code for the index expression.
			rightReg = visit(ast.right(), arg);
			
			// Restore previously stored value if needed.
			if(store) {
				leftReg = cg.rm.getRegister();
				cg.emit.emit("popl", leftReg);
			}
		} else {
			
			store = leftRN >= available;
			
			// Generate code for the index expression.
			rightReg = visit(ast.right(), arg);
						
			// Store the computed value if needed.
			if(store) {
				cg.emit.emit("pushl", rightReg);
				cg.rm.releaseRegister(rightReg);
			}
		
			// Generate code for the array to be indexed.
			leftReg = visit(ast.left(), arg);
			
			// Restore previously stored value if needed.
			if(store) {
				rightReg = cg.rm.getRegister();
				cg.emit.emit("popl", rightReg);
			}
		}
		
		/*
		 * Test whether rightReg holds a number not in [0, size-1] 
		 * and exit with code 3 if so.
		 */
		
		Register sizeReg = cg.rm.getRegister();
		String legalLabel = cg.emit.uniqueLabel();
		String illegalLabel = cg.emit.uniqueLabel();
		String nullLabel = cg.emit.uniqueLabel();
		
		// null check
		cg.emit.emit("cmp", AssemblyEmitter.constant(0), leftReg);
		cg.emit.emit("je", nullLabel);
		
		// index has to be greater than zero
		cg.emit.emit("cmp", AssemblyEmitter.constant(0), rightReg);
		cg.emit.emit("jl", illegalLabel);
		
		// load size of the array
		cg.emit.emitLoad(Config.SIZEOF_PTR, leftReg, sizeReg);
		
		// index has to be strictly smaller than array size
		cg.emit.emit("cmp", rightReg, sizeReg);
		cg.emit.emit("jle", illegalLabel);
		cg.emit.emit("jmp", legalLabel);
		
		// null exit
		cg.emit.emitLabel(nullLabel);
		BackendUtils.emitExit(cg.emit, ExitCode.NULL_POINTER, arg.SP_offset);
		
		// out of bounds exit
		cg.emit.emitLabel(illegalLabel);
		BackendUtils.emitExit(cg.emit, ExitCode.INVALID_ARRAY_BOUNDS, arg.SP_offset);
		
		// Ok -> nothing wrong
		cg.emit.emitLabel(legalLabel);
		
		// load address or value depending on requirements
		String op = return_value ? "movl" : "leal";
		cg.emit.emit(op, AssemblyEmitter.arrayAddress(leftReg, rightReg), leftReg);
		
		// release registers and store original value
		cg.rm.releaseRegister(rightReg);
		cg.rm.releaseRegister(sizeReg);
		//arg.returnValue = return_value;
		
		return leftReg;
	}

	@Override
	public Register intConst(IntConst ast, Context arg) {
		Register reg = cg.rm.getRegister();
		cg.emit.emitMove(AssemblyEmitter.constant(ast.value), reg);
		return reg;
	}

	@Override
	public Register field(Field ast, Context arg) {
		ClassSymbol sym = (ClassSymbol)ast.arg().type;
		boolean old_ret = arg.returnValue;
		int offset = -1;
		ClassSymbol curr = sym;
		while (offset == -1 && curr != ClassSymbol.objectType) {
			offset = sym.o_table.offsetOf(curr.name + "." + ast.fieldName);
			curr = curr.superClass;
		}
		
		arg.returnValue = true;
		
		System.out.println(ast.arg().getClass());
		System.out.println("Field: " + ast.fieldName);
		//assert ast.arg() instanceof ThisRef;
		
		Register reg = visit(ast.arg(), arg);
		assert reg != null;
		
		String labelOk = cg.emit.uniqueLabel();

		cg.emit.emit("cmp", constant(0), reg);
		cg.emit.emit("jne", labelOk);

		BackendUtils.emitExit(cg.emit, ExitCode.NULL_POINTER, arg.SP_offset);

		cg.emit.emitLabel(labelOk);

		String op = old_ret ? "movl" : "leal";
		cg.emit.emit(op, AssemblyEmitter.registerOffset(offset, reg), reg);
		return reg;
		
	}

	@Override
	public Register newArray(NewArray ast, Context arg) {
		Register sizeRegister = visit(ast.arg(), arg);
		
		/*
		 * Test whether reg holds a number less than 0 and exit with code 5 if so.
		 */
		String legalLabel = cg.emit.uniqueLabel();
		cg.emit.emit("cmp", AssemblyEmitter.constant(0), sizeRegister);
		cg.emit.emit("jge", legalLabel);
		BackendUtils.emitExit(cg.emit, ExitCode.INVALID_ARRAY_SIZE, arg.SP_offset);
		cg.emit.emitLabel(legalLabel);
		

		cg.emit.emit("addl", AssemblyEmitter.constant(2), sizeRegister);
		
		/*
		 * Save %eax on the stack if it is used.
		 */
		
		List<Register> toSave = new ArrayList<>();
		for(Register r : RegisterManager.CALLER_SAVE) {
			if(cg.rm.isInUse(r))
				toSave.add(r);
		}
		BackendUtils.saveRegisters(cg.emit, toSave);
		arg.SP_offset -= toSave.size() * Config.SIZEOF_PTR;
		
		/*
		 * Push arguments of calloc()
		 * Second:
		 * (size of an element, always 4 bytes in this case).
		 * First:
		 * Size: (+1 for the size and +1 for the vprt).
		 */
		List<String> args = Arrays.asList(sizeRegister.repr, 
							AssemblyEmitter.constant(Config.SIZEOF_PTR));
		BackendUtils.pushArguments(cg.emit, args, arg.SP_offset);
		
		// Call calloc.
		cg.emit.emit("call", Config.CALLOC);
		
		/*
		 * Move the result in %eax.
		 */
		Register arrayPointer = cg.rm.getRegister();
		cg.emit.emitMove(Register.EAX, arrayPointer);;
		
		/*
		 * Set the size and vtable.
		 */
		cg.emit.emit("leal", BackendUtils.getVTableArrayLabel(ast.typeName), Register.EAX);
		cg.emit.emitStore(Register.EAX, 0, arrayPointer);
		
		// free stack (args for calloc as well as caller registers)
		BackendUtils.popArguments(cg.emit, args, arg.SP_offset);
		BackendUtils.restoreRegisters(cg.emit, toSave);
		arg.SP_offset += toSave.size() * Config.SIZEOF_PTR;
		
		cg.emit.emit("subl", AssemblyEmitter.constant(2), sizeRegister);
		cg.emit.emitStore(sizeRegister, Config.SIZEOF_PTR, arrayPointer);
		cg.rm.releaseRegister(sizeRegister);
		
		return arrayPointer;
	}

	@Override
	public Register newObject(NewObject ast, Context arg) {
		List<Register> toSave = new ArrayList<>();
		for(Register r : RegisterManager.CALLER_SAVE) {
			if(cg.rm.isInUse(r))
				toSave.add(r);
		}
		BackendUtils.saveRegisters(cg.emit, toSave);
		arg.SP_offset -= toSave.size() * Config.SIZEOF_PTR;
		
		ClassSymbol symbol = (ClassSymbol)ast.type;
		List<String> args = Arrays.asList(AssemblyEmitter.constant(BackendUtils.size(symbol)),
										  AssemblyEmitter.constant(Config.SIZEOF_PTR));
		
		BackendUtils.pushArguments(cg.emit, args, arg.SP_offset);
		Register new_ref = cg.rm.getRegister();
		cg.emit.emit("call", Config.CALLOC);
		cg.emit.emitMove(Register.EAX, new_ref);
		
		cg.emit.emit("leal", symbol.v_table.name(), Register.EAX);
		cg.emit.emitStore(Register.EAX, 0, new_ref);
		
		BackendUtils.popArguments(cg.emit, args, arg.SP_offset);
		BackendUtils.restoreRegisters(cg.emit, toSave);
		arg.SP_offset += toSave.size() * Config.SIZEOF_PTR;
		
		return new_ref;
	}

	@Override
	public Register nullConst(NullConst ast, Context arg) {
		
		/*
		 * Get a free register and set it to 0.
		 */
		Register reg = cg.rm.getRegister();
		cg.emit.emit("xor", reg, reg);
		
		return reg;
	}

	@Override
	public Register thisRef(ThisRef ast, Context arg) {
		// get offset from base pointer, load to new register and return
		int this_offset = arg.getOffset("this");
		Register this_ref = cg.rm.getRegister();
		cg.emit.emitLoad(this_offset, Register.EBP, this_ref);
		return this_ref;
	}

	@Override
	public Register methodCall(MethodCallExpr ast, Context arg) {
		// store user caller registers
		List<Register> toSave = new ArrayList<>();
		for(Register r : RegisterManager.CALLER_SAVE) {
			if(cg.rm.isInUse(r))
				toSave.add(r);
		}
		BackendUtils.saveRegisters(cg.emit, toSave);
		arg.SP_offset -= toSave.size() * Config.SIZEOF_PTR;
		
		// reserve stack for arguments
		int argSize = ast.allArguments().size();
		int offset = BackendUtils.allocSpaceSize(arg.SP_offset, argSize);
		cg.emit.emit("subl", offset, Register.ESP);
		arg.SP_offset -= offset;
				
		// resolve receiver
		Register caller = visit(ast.receiver(), arg);
		cg.emit.emitStore(caller, 0, Register.ESP);
		cg.rm.releaseRegister(caller);
		
		// null check
		String labelOk = cg.emit.uniqueLabel();
		cg.emit.emit("cmp", constant(0), caller);
		cg.emit.emit("jne", labelOk);
		BackendUtils.emitExit(cg.emit, ExitCode.NULL_POINTER, arg.SP_offset);
		cg.emit.emitLabel(labelOk);
		
		// first store caller than args
		for (int i = 0; i < argSize - 1; i++) {
			Register res = visit(ast.argumentsWithoutReceiver().get(i), arg);
			cg.emit.emitStore(res, (i + 1) * Config.SIZEOF_PTR, Register.ESP);
			cg.rm.releaseRegister(res);
		}
		
		// find method in memory
		ClassSymbol receiver = (ClassSymbol)ast.receiver().type;
		int method_offset = receiver.v_table.methodOffset(ast.methodName);
		caller = cg.rm.getRegister();
		cg.emit.emitLoad(0, Register.ESP, caller);
		cg.emit.emitLoad(0, caller, caller);
		cg.emit.emit("addl", constant(method_offset), caller);
		cg.emit.emitLoad(0, caller, caller);
		
		// call method
		cg.emit.emit("call", "*" + caller.repr);
		
		// release stack
		cg.emit.emit("addl", constant(offset), Register.ESP);
		arg.SP_offset += offset;
		
		// move result from EAX to safe register
		cg.emit.emitMove(Register.EAX, caller);
		
		// restore user caller registers
		BackendUtils.restoreRegisters(cg.emit, toSave);
		arg.SP_offset += toSave.size() * Config.SIZEOF_PTR;
		
		return caller;
	}

	@Override
	public Register unaryOp(UnaryOp ast, Context arg) {
		Register argReg = visit(ast.arg(), arg);
		switch (ast.operator) {
		case U_PLUS:
			break;

		case U_MINUS:
			cg.emit.emit("negl", argReg);
			break;

		case U_BOOL_NOT:
			cg.emit.emit("negl", argReg);
			cg.emit.emit("incl", argReg);
			break;
		}
		return argReg;
	}
	
	@Override
	public Register var(Var ast, Context arg) {
		Register reg = cg.rm.getRegister();
		int offset = arg.getOffset(ast.sym.name);
		//System.out.println("Offset of: " + ast.sym.name + " : " + offset + " by value : " + arg.returnValue);
		String op = arg.returnValue ? "movl" : "leal";
		cg.emit.emit(op, AssemblyEmitter.registerOffset(offset, Register.EBP), reg);
		return reg;
	}
}
