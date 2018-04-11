package cd.frontend.semantic;

import cd.ir.*;
import cd.ir.Symbol.*;
import cd.ir.Ast.*;

public class SemanticVisitor extends AstVisitor<Object, Object> {

	/**
	 * TODO:
	 * - replace method bodies and check for semantic errors in them.
	 * - don't forget to visit their children
	 * - get inspired by previous homeworks
	 * - list of semantic errors is in javali specification on pages 8 and 9
	 * - good luck :)
	 */
	public Object assign(Ast.Assign ast, Object arg) {
		return dfltStmt(ast, arg);
	}

	public Object builtInWrite(Ast.BuiltInWrite ast, Object arg) {
		return dfltStmt(ast, arg);
	}

	public Object builtInWriteln(Ast.BuiltInWriteln ast, Object arg) {
		return dfltStmt(ast, arg);
	}
	
	public Object classDecl(Ast.ClassDecl ast, Object arg) {
		return dfltDecl(ast, arg);
	}
	
	public Object methodDecl(Ast.MethodDecl ast, Object arg) {
		return dfltDecl(ast, arg);
	}
			
	public Object varDecl(Ast.VarDecl ast, Object arg) {
		return dfltDecl(ast, arg);
	}
	
	public Object ifElse(Ast.IfElse ast, Object arg) {
		return dfltStmt(ast, arg);
	}
	
	public Object returnStmt(Ast.ReturnStmt ast, Object arg) {
		return dfltStmt(ast, arg);
	}

	public Object methodCall(Ast.MethodCall ast, Object arg) {
		return dfltStmt(ast, arg);
	}

	public Object nop(Ast.Nop ast, Object arg) {
		return dfltStmt(ast, arg);
	}
	
	public Object seq(Ast.Seq ast, Object arg) {
		return dflt(ast, arg);
	}
	
	public Object whileLoop(Ast.WhileLoop ast, Object arg) {
		return dfltStmt(ast, arg);
	}
}
