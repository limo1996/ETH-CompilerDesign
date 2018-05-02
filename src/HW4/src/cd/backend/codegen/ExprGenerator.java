package cd.backend.codegen;

import static cd.Config.SCANF;
import static cd.backend.codegen.AssemblyEmitter.constant;
import static cd.backend.codegen.RegisterManager.STACK_REG;

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
	
		boolean store;
		Register leftReg, rightReg;
		int leftRN = cg.rnv.calc(ast.left());
		int rightRN = cg.rnv.calc(ast.right());
		
		if (leftRN > rightRN) {
			
			/*
			 * Determine whether the intermediate result will have to be stored
			 * on the stack because the test of the computation uses all registers.
			 */
			store = rightRN >= RegisterManager.GPR.length;
			
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
			store = leftRN >= RegisterManager.GPR.length;
			
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
			cg.emit.emit("pushl",
					AssemblyEmitter.constant(ExitCode.DIVISION_BY_ZERO.value));
			cg.emit.emit("call", Config.EXIT);
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
		cg.emit.emit("sub", constant(offset), STACK_REG);
		cg.emit.emit("leal", AssemblyEmitter.registerOffset(8, STACK_REG), reg);
		cg.emit.emitStore(reg, 4, STACK_REG);
		cg.emit.emitStore("$STR_D", 0, STACK_REG);
		cg.emit.emit("call", SCANF);
		cg.emit.emitLoad(8, STACK_REG, reg);
		cg.emit.emit("add", constant(offset), STACK_REG);
		return reg;
	}

	@Override
	public Register cast(Cast ast, Context arg) {
		throw new ToDoException();
	}

	@Override
	public Register index(Index ast, Context arg) {
		
		boolean store;
		Register leftReg, rightReg;
		int leftRN = cg.rnv.calc(ast.left());
		int rightRN = cg.rnv.calc(ast.right());
		
		if(leftRN > rightRN) {
			
			store = rightRN >= RegisterManager.GPR.length;
			
			/*
			 * Generate code for the array to be indexed.
			 */
			leftReg = visit(ast.left(), arg);
			
			/*
			 * Store the computed value if needed.
			 */
			if(store) {
				cg.emit.emit("pushl", leftReg);
				cg.rm.releaseRegister(leftReg);
			}
			
			/*
			 * Generate code for the index expression.
			 */
			rightReg = visit(ast.right(), arg);
			
			/*
			 * Restore previously stored value if needed.
			 */
			if(store) {
				leftReg = cg.rm.getRegister();
				cg.emit.emit("popl", leftReg);
			}
		} else {
			
			store = leftRN >= RegisterManager.GPR.length;
			
			/*
			 * Generate code for the index expression.
			 */
			rightReg = visit(ast.right(), arg);
						
			/*
			 * Store the computed value if needed.
			 */
			if(store) {
				cg.emit.emit("pushl", rightReg);
				cg.rm.releaseRegister(rightReg);
			}
			
			/*
			 * Generate code for the array to be indexed.
			 */
			leftReg = visit(ast.left(), arg);
			
			/*
			 * Restore previously stored value if needed.
			 */
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
		cg.emit.emit("cmpl", AssemblyEmitter.constant(0), rightReg);
		cg.emit.emit("jl", illegalLabel);
		cg.emit.emitLoad(4, leftReg, sizeReg);
		cg.emit.emit("cmpl", sizeReg, rightReg);
		cg.emit.emit("jge", illegalLabel);
		cg.emit.emit("jmp", legalLabel);
		cg.emit.emitLabel(illegalLabel);
		cg.emit.emit("pushl",
				AssemblyEmitter.constant(ExitCode.INVALID_ARRAY_BOUNDS.value));
		cg.emit.emit("call", Config.EXIT);
		cg.emit.emitLabel(legalLabel);
		
		return null;
	}

	@Override
	public Register intConst(IntConst ast, Context arg) {
		Register reg = cg.rm.getRegister();
		cg.emit.emitMove(AssemblyEmitter.constant(ast.value), reg);
		return reg;
	}

	@Override
	public Register field(Field ast, Context arg) {
		throw new ToDoException();	
	}

	@Override
	public Register newArray(NewArray ast, Context arg) {
		
		Register reg = gen(ast.arg());
		boolean store = cg.rm.isInUse(Register.EAX);
		
		/*
		 * Test whether reg holds a number less than 0 and exit with code 5 if so.
		 */
		String legalLabel = cg.emit.uniqueLabel();
		cg.emit.emit("cmpl", AssemblyEmitter.constant(0), reg);
		cg.emit.emit("jge", legalLabel);
		cg.emit.emit("pushl",
				AssemblyEmitter.constant(ExitCode.INVALID_ARRAY_SIZE.value));
		cg.emit.emit("call", Config.EXIT);
		cg.emit.emitLabel(legalLabel);
		
		/*
		 * Save %eax on the stack if it is used.
		 */
		if(store)
			cg.emit.emit("pushl", Register.EAX);
		
		/*
		 * Push second argument of calloc()
		 * (size of an element, always 4 bytes in this case).
		 */
		cg.emit.emit("pushl", AssemblyEmitter.constant(Config.SIZEOF_PTR));
		
		/*
		 * Push number of elements (+1 for the size and +1 for the vprt).
		 */
		cg.emit.emit("addl", AssemblyEmitter.constant(2), reg);
		cg.emit.emit("pushl", reg);
		
		/*
		 * Call calloc.
		 */
		cg.emit.emit("call", Config.CALLOC);
		
		/*
		 * Restore reg in case it was used by calloc().
		 */
		cg.emit.emit("popl", reg);
		cg.emit.emit("subl", AssemblyEmitter.constant(2), reg);
		
		/*
		 * Clean the stack.
		 */
		cg.emit.emit("addl", AssemblyEmitter.constant(4), STACK_REG);
		
		/*
		 * Set the size.
		 */
		cg.emit.emitStore(reg, 4, Register.EAX);
		
		/*
		 * Move the returned pointer in %eax into another register 
		 * and restore %eax if necessary.
		 */
		cg.emit.emitMove(Register.EAX, reg);
		if(store)
			cg.emit.emit("popl", Register.EAX);
		
		return reg;
	}

	@Override
	public Register newObject(NewObject ast, Context arg) {
		throw new ToDoException();
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
		BackendUtils.saveRegisters(cg.emit, Arrays.asList(RegisterManager.CALLER_SAVE));
		arg.SP_offset -= RegisterManager.CALLER_SAVE.length * Config.SIZEOF_PTR;
		
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
		int method_offset = arg.getVTable().methodOffset(ast.methodName);
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
		BackendUtils.restoreRegisters(cg.emit, Arrays.asList(RegisterManager.CALLER_SAVE));
		arg.SP_offset += RegisterManager.CALLER_SAVE.length * Config.SIZEOF_PTR;
		
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
		String op = arg.returnValue ? "movl" : "leal";
		cg.emit.emit(op, AssemblyEmitter.registerOffset(offset, Register.EBP), reg);
		return reg;
	}
}
