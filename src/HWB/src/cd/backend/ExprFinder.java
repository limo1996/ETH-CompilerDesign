package cd.backend;

import cd.ir.Ast;
import cd.ir.AstVisitor;
import cd.ir.Ast.Expr;
import cd.ir.Ast.Stmt;
import cd.ir.Symbol.VariableSymbol;

//finds reads to given variable
	public class ExprFinder extends AstVisitor<Boolean, String>{
		public Boolean find(Stmt stmt, String var) {
			return visit(stmt, var);
		}
		
		public Boolean find(Expr expr, String var) {
			return visit(expr, var);
		}
		
		public Boolean onlyPrimitive(Stmt stmt) {
			return visit(stmt, "[Primitive]");
		}
		
		@Override
		public Boolean field(Ast.Field ast, String var) {
			if(var.equals("[field]"))
				return true;
			
			return visit(ast.arg(), var);
		}
		
		@Override
		public Boolean assign(Ast.Assign ast, String var) {
			//System.out.println("Assign ");
			return visit(ast.right(), var);
		}
		
		@Override
		public Boolean var(Ast.Var ast, String var) {
			//System.out.println("var " + ast.name);
			return ast.sym.kind == VariableSymbol.Kind.LOCAL && ast.name.equals(var);
		}
		
		@Override
		public Boolean nullConst(Ast.NullConst ast, String arg) {
			return false;
		}
		
		@Override
		public Boolean intConst(Ast.IntConst ast, String arg) {
			return false;
		}
		
		@Override
		public Boolean booleanConst(Ast.BooleanConst ast, String arg) {
			return false;
		}
		
		@Override
		public Boolean builtInRead(Ast.BuiltInRead ast, String arg) {
			return arg.equals("read");
		}
		
		@Override
		public Boolean methodCall(Ast.MethodCallExpr ast, String arg) {
			if(arg.equals("[methodCall]"))
				return true;
			
			boolean res = false;
			for(Expr e : ast.allArguments()) {
				res = res || visit(e, arg);
			}
			return res;
		}
		
		@Override
		public Boolean index(Ast.Index ast, String arg) {
			if(arg.equals("[index]"))
				return true;
			return visit(ast.left(), arg) || visit(ast.right(), arg);
		}
		
		@Override
		public Boolean builtInWrite(Ast.BuiltInWrite ast, String arg) {
			return visit(ast.arg(), arg);
		}
		
		@Override
		public Boolean builtInWriteln(Ast.BuiltInWriteln ast, String arg) {
			return false;
		}
		
		@Override
		public Boolean newObject(Ast.NewObject ast, String arg) {
			if(arg.equals("[newO]"))
				return true;
			return false;
		}

		@Override
		public Boolean newArray(Ast.NewArray ast, String arg) {
			if(arg.equals("[new]")) {
				return true;
			}
			return visit(ast.arg(), arg);
		}
		
		@Override
		public Boolean unaryOp(Ast.UnaryOp ast, String arg) {
			return visit(ast.arg(), arg);
		}
		
		@Override
		public Boolean binaryOp(Ast.BinaryOp ast, String arg) {
			if(arg.equals("[div/0]")) {
				if(ast.right() instanceof Ast.IntConst && ((Ast.IntConst)ast.right()).value == 0) {
					return true;
				}
			}
			return visit(ast.left(),arg) || visit(ast.right(), arg);
		}
		
		@Override
		public Boolean cast(Ast.Cast ast, String arg) {
			if(arg.equals("(cast)"))
				return true;
			return visit(ast.arg(), arg);
		}
		
		@Override
		public Boolean thisRef(Ast.ThisRef ast, String arg) {
			if(arg.equals("[this]"))
				return true;
			return false;
		}
		
		@Override
		public Boolean nop(Ast.Nop ast, String arg) {
			return false;
		}
		
		public Boolean returnStmt(Ast.ReturnStmt ast, String arg) {
			if(ast.arg() == null)
				return false;
			
			return visit(ast.arg(), arg);
		}

		@Override
		public Boolean methodCall(Ast.MethodCall ast, String arg) {
			return visit(ast.getMethodCallExpr(), arg);
		}
	}
