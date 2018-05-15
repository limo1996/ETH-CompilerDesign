package cd.backend;

import cd.ir.AstRewriteVisitor;
import cd.ir.BasicBlock;
import cd.ir.Symbol.PrimitiveTypeSymbol;
import cd.ir.Symbol.VariableSymbol;
import cd.transform.analysis.Definition;
import cd.transform.analysis.ReachingAnalysis;
import cd.util.debug.AstOneLine;
import cd.ir.Ast.*;

import java.util.ArrayList;
import java.util.List;

import cd.backend.codegen.AssemblyFailedException;
import cd.ir.Ast;

public class ConstantPropagation extends AstRewriteVisitor<Context> {

	@Override
	public Ast methodDecl(MethodDecl ast, Context arg) {
		ReachingAnalysis ra = new ReachingAnalysis(ast.cfg, ast);
		//ra.print();
		for(BasicBlock block : ast.cfg.allBlocks) {
			if(block.condition != null) {
				block.condition = (Expr)visit(block.condition, new Context(ra, block, block.stmts.size()));
			}
			for(int i = 0; i < block.stmts.size(); i++) {
				visit(block.stmts.get(i), new Context(ra, block,i));
			}
		}
		
		return ast;
	}
	
	@Override
	public Ast assign(Ast.Assign ast, Context arg) {
		ast.setRight((Expr)visit(ast.right(), arg));
		return ast;
	}
	
	public Ast var(Ast.Var ast, Context arg) {
		if(ast.sym.kind.equals(VariableSymbol.Kind.LOCAL) && ast.type instanceof PrimitiveTypeSymbol) {
			Expr repr = definition(ast.name, arg.curr_block, arg.curr_an, arg.stmt_index);
			if(repr == null)
				return ast;
			if(repr instanceof IntConst) {
				//System.out.println(ast.name + " Propagated!");
				return (IntConst)repr;
			} else if(repr instanceof BooleanConst) {
				//System.out.println("Propagated bool!");
				return (BooleanConst)repr;
			}
		}
		return ast;
	}
	
	private Expr definition(String var, BasicBlock bb, ReachingAnalysis ra, int i) {
		Expr reach = null;
		List<Expr> ins = new ArrayList<Expr>();
		for(Definition d : ra.inStates.get(bb)){
			if(d.var.equals(var)) {
				ins.add(d.expr);
			}
		}
		assert(ins.size() > 0);
		if(ins.size() == 1)
			reach = ins.get(0);
		else 
			reach = null;
		
		for(int it = i - 1; it >= 0; it--) {
			Stmt st = bb.stmts.get(it);
			if(st instanceof Assign && ((Assign)st).left() instanceof Var) {
				String name = ((Var)((Assign)st).left()).name;
				if(name.equals(var)) {
					reach = ((Assign)st).right();
					break;
				}
			}
		}
		
		/*Ast stmt = i < bb.stmts.size() ? bb.stmts.get(i) : bb.condition;
		System.out.println("Variable " + var + " in statement: \n" + 
				AstOneLine.toString(stmt) + "\nis defined as:\n" + 
				AstOneLine.toString(reach));*/

		return reach;
	}
	
	/**
	 * In unary op. we need to check whether we know the actual value at compile time.
	 * If yes we do the optimization.
	 */
	@Override
	public Ast unaryOp(UnaryOp ast, Context arg) {
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
	
	@Override
	public Ast binaryOp(BinaryOp ast, Context arg) {
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
		return ast;
	}
}
