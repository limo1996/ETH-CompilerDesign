package cd.frontend.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import cd.frontend.parser.JavaliParser.*;
import cd.ir.Ast.*;
import cd.util.Pair;
import cd.ir.Ast;

public final class JavaliAstVisitor extends JavaliBaseVisitor<List<Ast>> {

	public List<ClassDecl> classDecls = new ArrayList<>();
	
	/**
	 * Visits all children of provided node and returns them converted in List<Ast>.
	 */
	public List<Ast> visitChildren(ParserRuleContext prc){
		List<Ast> children = new ArrayList<Ast>();
		for(ParseTree pt: prc.children) {
			children.addAll(pt.accept(this));
		}
		return children;
	}
	
	/**
	 * Processes class declaration node, adds it to private list of classes and returns null
	 */
	@Override
	public List<Ast> visitClassDecl(ClassDeclContext ctx) {
		List<Ast> children = new ArrayList<Ast>();
		if(ctx.memberList() != null)
			children = ctx.memberList().accept(this);
		String superClass = "Object";
		if(ctx.Ident(1) != null)
			superClass = ctx.Ident(1).getText();
		ClassDecl cd = new ClassDecl(ctx.Ident(0).getText(), superClass, children);
		classDecls.add(cd);
		return null;
	}
	
	/**
	 * Goes through content of the class and returns List of variable and method declarations. 
	 */
	@Override 
	public List<Ast> visitMemberList(JavaliParser.MemberListContext ctx) { 
		return visitChildren(ctx);
	}
	
	/**
	 * Processes variable declaration and returns it as List of Ast nodes.
	 */
	@Override 
	public List<Ast> visitVarDecl(JavaliParser.VarDeclContext ctx) { 
		List<Ast> varDecls = new ArrayList<Ast>();
		String type = ctx.type().getText();
		for(TerminalNode node : ctx.Ident()) {
			varDecls.add(new VarDecl(type, node.getText()));
		}
		return varDecls;
	}
	
	/**
	 * Processes method declaration and returns it as in List of Ast nodes.
	 */
	@Override 
	public List<Ast> visitMethodDecl(JavaliParser.MethodDeclContext ctx) { 
		String name = ctx.Ident().getText();
		String returnType = "void";
		if(ctx.type() != null)
			returnType = ctx.type().getText();
		
		List<String> paramTypes = new ArrayList<String>();
		List<String> paramNames = new ArrayList<String>();
		FormalParamListContext fplc = ctx.formalParamList();
		if(fplc != null) {
			int typeSize = fplc.type().size();
			assert(typeSize == fplc.Ident().size());
			for(int i = 0; i < typeSize; i++) {
				paramTypes.add(fplc.type(i).getText());
				paramNames.add(fplc.type(i).getText());
			}
		}
		
		List<Ast> varDecls = new ArrayList<Ast>();
		List<Ast> stmtsDecls = new ArrayList<Ast>();
		if(ctx.varDecl() != null) {
			for(VarDeclContext vdc : ctx.varDecl()) {
				String type = vdc.type().getText();
				for(TerminalNode node : vdc.Ident()) {
					varDecls.add(new VarDecl(type, node.getText()));
				}
			}
		}
		
		if(ctx.stmt() != null) {
			for(StmtContext stmtc : ctx.stmt()) {
				stmtsDecls.addAll(stmtc.accept(this));
			}
		}
		
		MethodDecl md = new MethodDecl(returnType, name, paramTypes, paramNames, new Seq(varDecls), new Seq(stmtsDecls));
		return Arrays.asList(md);
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitStmtAssign(JavaliParser.StmtAssignContext ctx) { 
		return visitChildren(ctx); 
	}
	
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitStmtIf(JavaliParser.StmtIfContext ctx) { 
		return visitChildren(ctx); 
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitStmtWhile(JavaliParser.StmtWhileContext ctx) { 
		return visitChildren(ctx); 
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitReturnStmt(JavaliParser.ReturnStmtContext ctx) { 
		Expr expr = null;
		if(ctx.expr() != null)
			expr = (Expr)ctx.expr().accept(this);
		return Arrays.asList(new ReturnStmt(expr));
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitWrite(JavaliParser.WriteContext ctx) { 
		return Arrays.asList(new BuiltInWrite((Expr)ctx.expr().accept(this).get(0)));
	}
	
	/**
	 *
	 */
	@Override 
	public List<Ast> visitWriteln(JavaliParser.WritelnContext ctx) { 
		return Arrays.asList(new BuiltInWriteln());
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitAssignmentStmt(JavaliParser.AssignmentStmtContext ctx) { 
		Expr left = (Expr)ctx.identAccess().accept(this).get(0);
		Expr right;
		if(ctx.expr() != null)
			right = (Expr)ctx.expr().accept(this).get(0);
		else if(ctx.newExpr() != null)
			right = (Expr)ctx.newExpr().accept(this).get(0);
		else
			right = (Expr)ctx.readExpr().accept(this).get(0);
		return Arrays.asList(new Assign(left, right));
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitNewObj(JavaliParser.NewObjContext ctx) { 
		return Arrays.asList(new NewObject(ctx.Ident().getText()));
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitNewIArray(JavaliParser.NewIArrayContext ctx) { 
		String typeName = ctx.Ident().getText();
		Expr capacity = (Expr)ctx.expr().accept(this).get(0);
		return Arrays.asList(new NewArray(typeName, capacity));
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitNewPArray(JavaliParser.NewPArrayContext ctx) { 
		String typeName = ctx.primitiveType().getText();
		Expr capacity = (Expr)ctx.expr().accept(this).get(0);
		return Arrays.asList(new NewArray(typeName, capacity));
	}
	
	/**
	 * 
	 *
	@Override 
	public List<Ast> visitIdentAccess(JavaliParser.IdentAccessContext ctx) { 
		if(ctx.getText() == "this")
			return Arrays.asList(new ThisRef());
		if(ctx.Iden)
	}*/
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitIaIdent(JavaliParser.IaIdentContext ctx) { 
		return Arrays.asList(new Var(ctx.Ident().getText()));
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitIaIaMethodCall(JavaliParser.IaIaMethodCallContext ctx) { 
		String name = ctx.Ident().getText();
		Expr recv = (Expr)ctx.identAccess().accept(this).get(0);
		
		@SuppressWarnings("unchecked")
		List<Expr> args = (List<Expr>)(List<?>)ctx.actualParamList().accept(this);
		
		return Arrays.asList(new MethodCallExpr(recv, name, args));
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitIaMethodCall(JavaliParser.IaMethodCallContext ctx) { 
		String name = ctx.Ident().getText();
		Expr recv = new ThisRef();
		
		@SuppressWarnings("unchecked")
		List<Expr> args = (List<Expr>)(List<?>)ctx.actualParamList().accept(this);
		
		return Arrays.asList(new MethodCallExpr(recv, name, args));
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitIaIaIdent(JavaliParser.IaIaIdentContext ctx) { 
		List<Ast> ia = ctx.identAccess().accept(this);
		ia.get(0).children().add(new Var(ctx.Ident().getText()));
		return ia;
	}
	
	/**
	 * 
	 */
	@Override public List<Ast> visitIaArrayAccess(JavaliParser.IaArrayAccessContext ctx) { 
		Expr array = (Expr)ctx.identAccess().accept(this).get(0);
		Expr index = (Expr)ctx.expr().accept(this).get(0);
		return Arrays.asList(new Index(array, index));
	}
	
	/**
	 * 
	 */
	@Override public List<Ast> visitIaThis(JavaliParser.IaThisContext ctx) { 
		return Arrays.asList(new ThisRef());
	}
	
	/**
	 *
	 */
	@Override 
	public List<Ast> visitMethCall(JavaliParser.MethCallContext ctx) { 
		return visitChildren(ctx); 
	}
	
	/**
	 * 
	 */
	@Override 
	public List<Ast> visitMethIaCall(JavaliParser.MethIaCallContext ctx) { 
		return visitChildren(ctx); 
	}
}
