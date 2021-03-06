package cd.backend;

import java.util.ArrayList;
import java.util.List;

import cd.backend.codegen.AssemblyFailedException;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.AstRewriteVisitor;
import cd.ir.BasicBlock;
import cd.ir.Ast.*;
import cd.ir.Symbol.PrimitiveTypeSymbol;
import cd.util.debug.AstOneLine;
import cd.ir.Ast;

public class ConstantFolderVisitor extends AstRewriteVisitor<Void> {

	@Override
	public Ast methodDecl(MethodDecl ast, Void arg) {	
		if(ast.cfg == null) {
			visit(ast.body(), arg);
		} else {
			for(BasicBlock bb : ast.cfg.allBlocks) {
				if(bb.condition != null)
					bb.condition = (Expr) visit(bb.condition, arg);
				
				for(Stmt stmt : bb.stmts) {
					visit(stmt, arg);
				}
			}
		}
		return ast;
	}
	
	/**
	 * In unary op. we need to check whether we know the actual value at compile time.
	 * If yes we do the optimization.
	 */
	@Override
	public Ast unaryOp(UnaryOp ast, Void arg) {
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
		} else if(arg_e instanceof UnaryOp) {
			UnaryOp nested = (UnaryOp)arg_e;
			if(ast.operator.equals(UnaryOp.UOp.U_MINUS) && nested.operator.equals(UnaryOp.UOp.U_MINUS)) {
				return nested.arg();
			} else if((ast.operator.equals(UnaryOp.UOp.U_PLUS) && nested.operator.equals(UnaryOp.UOp.U_MINUS)) ||
					ast.operator.equals(UnaryOp.UOp.U_MINUS) && nested.operator.equals(UnaryOp.UOp.U_PLUS)) {
				return new UnaryOp(UnaryOp.UOp.U_MINUS, nested.arg());
			} else if(ast.operator.equals(UnaryOp.UOp.U_PLUS) && nested.operator.equals(UnaryOp.UOp.U_PLUS)) {
				return nested.arg();
			} else if(ast.operator.equals(UnaryOp.UOp.U_BOOL_NOT) && nested.operator.equals(UnaryOp.UOp.U_BOOL_NOT)) {
				return nested.arg();
			}
		}
		return to_ret;
	}
	@Override
	public Ast assign(Ast.Assign ast, Void arg) {
		ast.setRight((Expr)visit(ast.right(), arg));
		return ast;
	}
	@Override
	public Ast classDecl(Ast.ClassDecl ast, Void arg) {
		return visitChildren(ast, arg);
	}
	
	
	/**
	 * TODO: 
	 * x * 0 => 0
	 * x + 0 || 0 + x => x
	 * x - 0 => x
	 * 0 - x => -x
	 * 1 * x => x
	 * x / 1 => x
	 * true || x => true
	 * false && x => false
	 */
	
	/**
	 * In binary op we can perform whole bunch of optimizations. 
	 * Some of them are listed above and the others are when we know 
	 * actual values at compile time.
	 */
	@Override
	public Ast binaryOp(BinaryOp ast, Void arg) {
		Ast left = visit(ast.left(), arg);
		Ast right = visit(ast.right(), arg);
		if(left instanceof IntConst && right instanceof IntConst) {
			int left_v = ((IntConst)left).value;
			int right_v = ((IntConst)right).value;
			//System.out.println(left_v + " " + right_v);
			switch(ast.operator) {
				case B_TIMES:
					return new IntConst(left_v * right_v);
				case B_PLUS:
					return new IntConst(left_v + right_v);
				case B_MINUS:
					return new IntConst(left_v - right_v);
				case B_DIV:
					if(right_v == 0) {
						ast.setLeft((Expr)left);
						ast.setRight((Expr)right);
						return ast;
					}
					return new IntConst(left_v / right_v);
				case B_MOD:
					if(right_v == 0) {
						ast.setLeft((Expr)left);
						ast.setRight((Expr)right);
						return ast;
					}
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
		} else {
			boolean l_known = false, r_known = false;
			int l_val = 0, r_val = 0;
			if(left instanceof IntConst) {
				l_known = true;
				l_val = ((IntConst)left).value;
			} else if(right instanceof IntConst) {
				r_known = true;
				r_val = ((IntConst)right).value;
			}
				
			// x * 0 => 0
			// x + 0 || 0 + x => x
			// x - 0 => x
			// 0 - x => -x
			if(l_known) {
				switch(ast.operator) {
				case B_TIMES:
					if(l_val == 0) {
						return new IntConst(0);
					}
					else if(l_val == 1) {
						return right;
					}
					break;
				case B_PLUS:
					if(l_val == 0) {
						return right;
					}
					break;
				case B_MINUS:
					if(l_val == 0) {
						return new UnaryOp(UnaryOp.UOp.U_MINUS, (Expr)right);
					}
					break;
				}
			} else if (r_known){
				switch(ast.operator) {
				case B_TIMES:
					if(r_val == 0) {
						return new IntConst(0);
					} else if(r_val == 1) {
						return left;
					}
					break;
				case B_PLUS:
					if(r_val == 0) {
						return left;
					}
					break;
				case B_MINUS:
					if(r_val == 0) {
						return left;
					}
					break;
				case B_DIV:
					if(r_val == 1) {
						return left;
					}
					break;
				}
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
		} else {
			boolean val = true, set = true;
			if(left instanceof BooleanConst) {
				val = ((BooleanConst)left).value;
			} else if (right instanceof BooleanConst) {
				val = ((BooleanConst)right).value;
			} else {
				set = false;
			}
			
			if(set) {
				if(ast.operator.equals(BinaryOp.BOp.B_AND) && !val)
					return new BooleanConst(false);
				if(ast.operator.equals(BinaryOp.BOp.B_OR) && val)
					return new BooleanConst(true);
			}
		}
		ast.setLeft((Expr)left);
		ast.setRight((Expr)right);
		return rebalanceInt((Expr)left, ast.operator, (Expr)right, ast);
	}
	
	private BinaryOp rebalanceInt(Expr left, BinaryOp.BOp bop, Expr right, BinaryOp original) {
		BinaryOp child;
		IntConst con, con2;
		if(left instanceof IntConst && right instanceof BinaryOp) {
			con = (IntConst)left;
			child = (BinaryOp)right;
		} else if(left instanceof BinaryOp && right instanceof IntConst) {
			con = (IntConst)right;
			child = (BinaryOp)left;
		} else {
			return original;
		}
		
		if(!bop.equals(BinaryOp.BOp.B_PLUS) && !bop.equals(BinaryOp.BOp.B_TIMES)) {
			return original;
		}
		
		Expr other;
		if(child.left() instanceof IntConst) {
			con2 = (IntConst)child.left();
			other = child.right();
		} else if(child.right() instanceof IntConst) {
			con2 = (IntConst)child.right();
			other = child.left();
		} else {
			return original;
		}
		if(!child.operator.equals(bop))
			return original;
		
		//System.out.println("Rebalance applied on " + AstOneLine.toString(original));
		original.setLeft(other);
		if(bop.equals(BinaryOp.BOp.B_PLUS))
			original.setRight(new IntConst(con.value + con2.value));
		else
			original.setRight(new IntConst(con.value * con2.value));
		//System.out.println("to " + AstOneLine.toString(original));
		return original;
	}
}
