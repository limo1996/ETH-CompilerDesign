package cd.backend;

import cd.backend.codegen.AssemblyFailedException;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.AstRewriteVisitor;
import cd.ir.BasicBlock;
import cd.ir.Ast.*;
import cd.ir.Symbol.PrimitiveTypeSymbol;
import cd.ir.Ast;

public class ConstantFolderVisitor extends AstRewriteVisitor<Void> {

	@Override
	public Ast unaryOp(UnaryOp ast, Void arg) {
		System.out.println("UnaryyOp");
		Ast to_ret = ast;
		Expr arg_e = (Expr)this.visit(ast.arg(), arg);
		if(arg_e instanceof IntConst) {
			int val = ((IntConst)arg_e).value;
			if(ast.operator.equals(UnaryOp.UOp.U_MINUS)) {
				val = (-1) * val;
			}
			to_ret = new IntConst(val);
		} else if (arg_e instanceof BooleanConst) {
			boolean val = ((BooleanConst)arg_e).value;
			if(ast.operator.equals(UnaryOp.UOp.U_BOOL_NOT)) {
				val = !val;
			}
			to_ret = new BooleanConst(val);
		}
		return to_ret;
	}
	
	public Ast assign(Ast.Assign ast, Void arg) {
		visit(ast.right(), arg);
		return ast;
	}
	
	public Ast classDecl(Ast.ClassDecl ast, Void arg) {
		visitChildren(ast, arg);
		return ast;
	}
	
	public Ast seq(Ast.Seq ast, Void arg) {
		visitChildren(ast, arg);
		return ast;
	}
	public Ast varDecl(Ast.VarDecl ast, Void arg) {
		visitChildren(ast, arg);
		return ast;
	}
	
	public Ast methodDecl(Ast.MethodDecl ast, Void arg) {
		for (BasicBlock blk : ast.cfg.allBlocks) {			
			for(Stmt stmt : blk.stmts) {
				visit(stmt, arg);
			}
		}
		return ast;
	}
	
	@Override
	public Ast binaryOp(BinaryOp ast, Void arg) {
		Ast left = visit(ast.left(), arg);
		Ast right = visit(ast.right(), arg);
		if(left instanceof IntConst && right instanceof IntConst) {
			int left_v = ((IntConst)left).value;
			int right_v = ((IntConst)right).value;
			System.out.println(left_v + " " + right_v);
			switch(ast.operator) {
				case B_TIMES:
					return new IntConst(left_v * right_v);
				case B_PLUS:
					return new IntConst(left_v + right_v);
				case B_MINUS:
					return new IntConst(left_v - right_v);
				case B_DIV:
					return new IntConst(left_v / right_v);
				case B_MOD:
					return new IntConst(left_v % right_v);
				case B_LESS_THAN:
					return new BooleanConst(left_v < right_v);
				case B_LESS_OR_EQUAL:
					return new BooleanConst(left_v <= right_v);
				case B_GREATER_THAN:
					return new BooleanConst(left_v > right_v);
				case B_GREATER_OR_EQUAL:
					return new BooleanConst(left_v >= right_v);
				case B_EQUAL:
					return new BooleanConst(left_v == right_v);
				case B_NOT_EQUAL:
					return new BooleanConst(left_v != right_v);
			default:
				throw new AssemblyFailedException(
						"Invalid binary operator for "
								+ PrimitiveTypeSymbol.intType + " or "
								+ PrimitiveTypeSymbol.booleanType);
			}
		}
		
		if(left instanceof BooleanConst && right instanceof BooleanConst) {
			boolean left_v = ((BooleanConst)left).value;
			boolean right_v = ((BooleanConst)right).value;
			
			switch(ast.operator) {
				case B_AND:
					return new BooleanConst(left_v && right_v);
				case B_OR:
					return new BooleanConst(left_v || right_v);
				case B_EQUAL:
					return new BooleanConst(left_v == right_v);
				case B_NOT_EQUAL:
					return new BooleanConst(left_v != right_v);
			default:
				throw new AssemblyFailedException(
						"Invalid binary operator for "
								+ PrimitiveTypeSymbol.intType + " or "
								+ PrimitiveTypeSymbol.booleanType);
			}
		}
		return ast;
	}
}
