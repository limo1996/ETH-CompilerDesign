package cd.backend.codegen;

import static cd.Config.SCANF;
import static cd.backend.codegen.AssemblyEmitter.constant;
import static cd.backend.codegen.RegisterManager.STACK_REG;

import java.util.Arrays;
import java.util.List;

import cd.ToDoException;
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
import cd.ir.Symbol.PrimitiveTypeSymbol;
import cd.util.debug.AstOneLine;

/**
 * Generates code to evaluate expressions. After emitting the code, returns a
 * String which indicates the register where the result can be found.
 */
class ExprGenerator extends ExprVisitor<Register, Void> {
	protected final AstCodeGenerator cg;

	ExprGenerator(AstCodeGenerator astCodeGenerator) {
		cg = astCodeGenerator;
	}

	public Register gen(Expr ast) {
		return visit(ast, null);
	}

	@Override
	public Register visit(Expr ast, Void arg) {
		try {
			cg.emit.increaseIndent("Emitting " + AstOneLine.toString(ast));
			return super.visit(ast, null);
		} finally {
			cg.emit.decreaseIndent();
		}

	}
	
	/*
	 * Compares the contents of leftReg and rightReg and sets leftReg according
	 * to whether they are members of the relation
	 * (op is the corresponding assembly jump instruction).
	 * 
	 * This method is needed because the SETcc (conditional set) instruction
	 * works only on byte registers and a reasonable alternative has no been found. 
	 */
	public void relationalOperator(String op, Register leftReg, Register rightReg) {
		
		String trueLabel = cg.emit.uniqueLabel();
		String falseLabel = cg.emit.uniqueLabel();
		
		cg.emit.emit("cmpl", rightReg, leftReg);
		cg.emit.emit(op, trueLabel);
		cg.emit.emitMove(AssemblyEmitter.constant(0), leftReg);
		cg.emit.emit("jmp", falseLabel);
		cg.emit.emitLabel(trueLabel);
		cg.emit.emitMove(AssemblyEmitter.constant(1), leftReg);
		cg.emit.emitLabel(falseLabel);
	}
	
	@Override
	public Register binaryOp(BinaryOp ast, Void arg) {
	
		int leftRN = cg.rnv.calc(ast.left());
		int rightRN = cg.rnv.calc(ast.right());

		Register leftReg, rightReg;
		if (leftRN > rightRN) {
			
			leftReg = gen(ast.left());
			rightReg = gen(ast.right());
			
		} else {
			
			rightReg = gen(ast.right());
			
			/*
			 * Store RHS on the stack if needed.
			 */
			if(leftRN >= 6) {
				cg.emit.emit("pushl", rightReg);
				cg.rm.releaseRegister(rightReg);
			}
			
			leftReg = gen(ast.left());
			
			/*
			 *  Reload RHS from the stack if needed.
			 */
			if(leftRN >= 6) {
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
			cg.emit.emit("pushl", AssemblyEmitter.constant(7));
			cg.emit.emit("call", "exit");
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
			relationalOperator("je", leftReg, rightReg);
			break;
			
		case B_NOT_EQUAL:
			relationalOperator("jne", leftReg, rightReg);
			break;
			
		case B_LESS_THAN:
			relationalOperator("jb", leftReg, rightReg);
			break;
			
		case B_LESS_OR_EQUAL:
			relationalOperator("jbe", leftReg, rightReg);
			break;
			
		case B_GREATER_THAN:
			relationalOperator("ja", leftReg, rightReg);
			break;
			
		case B_GREATER_OR_EQUAL:
			relationalOperator("jae", leftReg, rightReg);
			break;
		}

		cg.rm.releaseRegister(rightReg);

		return leftReg;
	}

	@Override
	public Register booleanConst(BooleanConst ast, Void arg) {
		
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
	public Register builtInRead(BuiltInRead ast, Void arg) {
		Register reg = cg.rm.getRegister();
		cg.emit.emit("sub", constant(16), STACK_REG);
		cg.emit.emit("leal", AssemblyEmitter.registerOffset(8, STACK_REG), reg);
		cg.emit.emitStore(reg, 4, STACK_REG);
		cg.emit.emitStore("$STR_D", 0, STACK_REG);
		cg.emit.emit("call", SCANF);
		cg.emit.emitLoad(8, STACK_REG, reg);
		cg.emit.emit("add", constant(16), STACK_REG);
		return reg;
	}

	@Override
	public Register cast(Cast ast, Void arg) {
		throw new ToDoException();
	}

	@Override
	public Register index(Index ast, Void arg) {
		throw new ToDoException();
	}

	@Override
	public Register intConst(IntConst ast, Void arg) {
		Register reg = cg.rm.getRegister();
		cg.emit.emitMove(AssemblyEmitter.constant(ast.value), reg);
		return reg;
	}

	@Override
	public Register field(Field ast, Void arg) {
		if(ast.type instanceof PrimitiveTypeSymbol)
			
	}

	@Override
	public Register newArray(NewArray ast, Void arg) {
		throw new ToDoException();
	}

	@Override
	public Register newObject(NewObject ast, Void arg) {
		throw new ToDoException();
	}

	@Override
	public Register nullConst(NullConst ast, Void arg) {
		
		/*
		 * Get a free register and set it to 0.
		 */
		Register reg = cg.rm.getRegister();
		cg.emit.emitMove(AssemblyEmitter.constant(0), reg);
		
		return reg;
	}

	@Override
	public Register thisRef(ThisRef ast, Void arg) {
		throw new ToDoException();
	}

	@Override
	public Register methodCall(MethodCallExpr ast, Void arg) {
		throw new ToDoException();
	}

	@Override
	public Register unaryOp(UnaryOp ast, Void arg) {
		Register argReg = gen(ast.arg());
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
	public Register var(Var ast, Void arg) {
		Register reg = cg.rm.getRegister();
		cg.emit.emitMove(AstCodeGenerator.VAR_PREFIX + ast.name, reg);
		return reg;
	}
}
