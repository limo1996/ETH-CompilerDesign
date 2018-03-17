package cd.backend.codegen;

import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast.BinaryOp;
import cd.ir.Ast.BooleanConst;
import cd.ir.Ast.BuiltInRead;
import cd.ir.Ast.Cast;
import cd.ir.Ast.Expr;
import cd.ir.Ast.Field;
import cd.ir.Ast.Index;
import cd.ir.Ast.IntConst;
import cd.ir.Ast.NewArray;
import cd.ir.Ast.NewObject;
import cd.ir.Ast.NullConst;
import cd.ir.Ast.ThisRef;
import cd.ir.Ast.UnaryOp;
import cd.ir.Ast.Var;
import cd.ir.Ast;
import cd.ir.ExprVisitor;
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

	@Override
	public Register binaryOp(BinaryOp ast, Void arg) {
		assert(ast.children().size() == 2);
		Expr left = (Expr)ast.children().get(0);
		Expr right = (Expr)ast.children().get(1);
		boolean right_first = false;
		if(left.getRegNeeded() <= right.getRegNeeded()) {
			right_first = true;
		}
		Register r, l;
		if(right_first) {
			r = right.accept(this, arg);
			l = left.accept(this, arg);
		} else {
			l = left.accept(this, arg);
			r = right.accept(this, arg);
		}
		cg.emit.emitComment(l.repr + " = " + l.repr + " " + ast.operator.repr + " " + r.repr);
		switch(ast.operator) {
		case B_PLUS:
			cg.emit.emit("addl", r, l);
			break;
		case B_MINUS:
			cg.emit.emit("subl", r, l);
			break;
		case B_TIMES:
			cg.emit.emit("imull", r, l);
			break;
		case B_DIV:
			if(cg.rm.isInUse(Register.EAX) && Register.EAX != l) {
				cg.emit.emitComment("EAX in use, we need to store it on the stack prior to div");
				cg.emit.emitStore(Register.EAX, -4, Register.ESP);
			}
			if(cg.rm.isInUse(Register.EDX)) {
				cg.emit.emitComment("EDX in use, we need to store it on the stack prior to div");
				cg.emit.emitStore(Register.EDX, -8, Register.ESP);
			}
			cg.emit.emitMove(l, Register.EAX);
			cg.emit.emitRaw("cdq");
			if(r == Register.EAX) {
				cg.emit.emitComment("Division by %EAX, that is stored on the stack");
				cg.emit.emit("idivl", AssemblyEmitter.registerOffset(-4, Register.ESP));
			} else if(r == Register.EDX){
				cg.emit.emitComment("Division by %EDX, that is stored on the stack");
				cg.emit.emit("idivl", AssemblyEmitter.registerOffset(-8, Register.ESP));
			} else {
				cg.emit.emit("idivl", r);
			}
			cg.emit.emitMove(Register.EAX, l);
			if (cg.rm.isInUse(Register.EAX) && Register.EAX != l) {
				cg.emit.emitComment("Restore EAX after div");
				cg.emit.emitMove(AssemblyEmitter.registerOffset(-4, Register.ESP), Register.EAX);
			}
			if (cg.rm.isInUse(Register.EDX) && Register.EDX != l) {
				cg.emit.emitComment("Restore EDX after div");
				cg.emit.emitMove(AssemblyEmitter.registerOffset(-8, Register.ESP), Register.EDX);
			}
			break;
		default:
			throw new RuntimeException(ast.operator + " operator not supported!");
		}
		cg.rm.releaseRegister(r);
		return l;
	}

	@Override
	public Register booleanConst(BooleanConst ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register builtInRead(BuiltInRead ast, Void arg) {
		assert(ast.children().size() == 0);
		Register res = cg.rm.getRegister();
		cg.emit.emitComment("--- Read ---");
		cg.emit.emit("subl", AssemblyEmitter.constant(8), Register.ESP);
		cg.emit.emitMove(AssemblyEmitter.labelAddress(StmtGenerator.READ_VAL), AssemblyEmitter.registerOffset(4, Register.ESP));
		cg.emit.emitMove(AssemblyEmitter.labelAddress(StmtGenerator.STR_D), AssemblyEmitter.registerOffset(0, Register.ESP));
		cg.emit.emit("calll", cd.Config.SCANF);
		cg.emit.emitMove(StmtGenerator.READ_VAL, res);
		cg.emit.emit("addl", AssemblyEmitter.constant(8), Register.ESP);
		cg.emit.emitComment("---------");
		return res;
	}

	@Override
	public Register cast(Cast ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register index(Index ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	/**
	 * Load int constant into register
	 */
	@Override
	public Register intConst(IntConst ast, Void arg) {
		Register r = cg.rm.getRegister();
		cg.emit.emitComment("Load of integer constant " + ast.value);
		cg.emit.emitMove(AssemblyEmitter.constant(ast.value), r);
		return r;
	}

	@Override
	public Register field(Field ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register newArray(NewArray ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register newObject(NewObject ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register nullConst(NullConst ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register thisRef(ThisRef ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	/**
	 * Generates code for unary operator. In fact we should support just negation.
	 * So emit negl.
	 */
	@Override
	public Register unaryOp(UnaryOp ast, Void arg) {
		assert(ast.children().size() == 1);
		Register r = ((Expr)ast.children().get(0)).accept(this, arg);
		switch(ast.operator) {
		case U_BOOL_NOT:
			cg.emit.emit("notl", r);
			break;
		case U_MINUS:
			cg.emit.emitComment("Negation of " + r.repr);
			cg.emit.emit("negl", r);
			break;
		default:
		}
		return r;
	}
	
	/**
	 * Load variable into free register.
	 */
	@Override
	public Register var(Var ast, Void arg) {
		Register r = cg.rm.getRegister();
		cg.emit.emitComment("Load of variable " + ast.name + " into register " + r.repr);
		cg.emit.emitMove(ast.name, r);
		return r;
	}
}
