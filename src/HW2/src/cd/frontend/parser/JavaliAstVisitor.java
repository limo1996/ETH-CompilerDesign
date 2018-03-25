package cd.frontend.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import cd.frontend.parser.JavaliParser.*;
import cd.ir.Ast.*;
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
		if(ctx.children == null)
			return new ArrayList<Ast>();
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
				paramNames.add(fplc.Ident(i).getText());
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
	 * Creates function parameter list by visiting all expressions passed as arguments.
	 */
	@Override 
	public List<Ast> visitActualParamList(JavaliParser.ActualParamListContext ctx) { 
		List<Ast> exprs = new ArrayList<Ast>();
		for(JavaliParser.ExprContext ectx : ctx.expr()) {
			exprs.addAll(ectx.accept(this));
		}
		return exprs;
	}
	
	/**
	 * Visits expression in return statement and then returns ReturnStmt object with
	 * expression as parameter.
	 */
	@Override 
	public List<Ast> visitReturnStmt(JavaliParser.ReturnStmtContext ctx) { 
		Expr expr = null;
		if(ctx.expr() != null)
			expr = (Expr)ctx.expr().accept(this).get(0);
		return Arrays.asList(new ReturnStmt(expr));
	}
	
	/**
	 * Visits all statements in the block and returns list of them.
	 */
	@Override 
	public List<Ast> visitStmtBlock(JavaliParser.StmtBlockContext ctx) { 
		List<Ast> block = new ArrayList<Ast>();
		
		for (JavaliParser.StmtContext stmt : ctx.stmt()) {
            block.addAll(stmt.accept(this));
        }

        return block;
	}
	
	/**
	 * Visits expression in if statement, then block of statements and otherwise block 
	 * as well if provided. Returns IfElse object.
	 */
	@Override 
	public List<Ast> visitIfStmt(JavaliParser.IfStmtContext ctx) { 
		Expr cond = (Expr)ctx.expr().accept(this).get(0);
		Ast then = new Seq(ctx.stmtBlock(0).accept(this));
		Ast otherwise = new Nop();
		
		if(ctx.stmtBlock().size() == 2) {
			otherwise = new Seq(ctx.stmtBlock(1).accept(this));
		}
		
		return Arrays.asList(new IfElse(cond, then, otherwise));
	}
	
	/**
	 * Visits expression in while loop condition and statements in body of while loop.
	 * Returns WhileLoop object.
	 */
	@Override 
	public List<Ast> visitWhileStmt(JavaliParser.WhileStmtContext ctx) { 
		Expr cond = (Expr)ctx.expr().accept(this).get(0);
		Ast body = new Seq(ctx.stmtBlock().accept(this));
		return Arrays.asList(new WhileLoop(cond, body));
	}
	
	/**
	 * Returns BuiltInWrite object with visited expression in it.
	 */
	@Override 
	public List<Ast> visitWrite(JavaliParser.WriteContext ctx) { 
		return Arrays.asList(new BuiltInWrite((Expr)ctx.expr().accept(this).get(0)));
	}
	
	/**
	 * Returns BuiltInWriteln object.
	 */
	@Override 
	public List<Ast> visitWriteln(JavaliParser.WritelnContext ctx) { 
		return Arrays.asList(new BuiltInWriteln());
	}
	
	/**
	 * Visits BuiltInRead object.
	 */
	@Override 
	public List<Ast> visitRead(JavaliParser.ReadContext ctx) { 
		return Arrays.asList(new BuiltInRead());
	}
	
	/**
	 * Visits left and right part of assignment statements depending on assignement type
	 * and returns Assign object.
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
	 * Returns NewObject object with identifier passed as constructor parameter.
	 */
	@Override 
	public List<Ast> visitNewObj(JavaliParser.NewObjContext ctx) { 
		return Arrays.asList(new NewObject(ctx.Ident().getText()));
	}
	
	/**
	 * Visits capacity expression and returns NewArray object of custom type.
	 */
	@Override 
	public List<Ast> visitNewIArray(JavaliParser.NewIArrayContext ctx) { 
		String typeName = ctx.Ident().getText() + "[]";
		Expr capacity = (Expr)ctx.expr().accept(this).get(0);
		return Arrays.asList(new NewArray(typeName, capacity));
	}
	
	/**
	 * Visits capacity expression and returns NewArray object of primitive type.
	 */
	@Override 
	public List<Ast> visitNewPArray(JavaliParser.NewPArrayContext ctx) { 
		String typeName = ctx.primitiveType().getText() + "[]";
		Expr capacity = (Expr)ctx.expr().accept(this).get(0);
		return Arrays.asList(new NewArray(typeName, capacity));
	}
	
	/**
	 * Visits parameters of method call. Receiver is this object.
	 * Returns MethodCallExpr wrapped in MethodCall object.
	 */
	@Override 
	public List<Ast> visitMethCall(JavaliParser.MethCallContext ctx) { 
		String name = ctx.Ident().getText();
		Expr recv = new ThisRef();
		
		List<Expr> args = new ArrayList<Expr>();
		if(ctx.actualParamList() != null)
			args = (List<Expr>)(List<?>)ctx.actualParamList().accept(this);
		
		return Arrays.asList(new MethodCall(new MethodCallExpr(recv, name, args)));
	}
	
	/**
	 * Visits parameters of method call. Visits receiver.
	 * Returns MethodCallExpr wrapped in MethodCall object.
	 */
	@Override 
	public List<Ast>  visitMethIaCall(JavaliParser.MethIaCallContext ctx) { 
		String name = ctx.Ident().getText();
		Expr recv = (Expr)ctx.identAccess().accept(this).get(0);
		
		List<Expr> args = new ArrayList<Expr>();
		if(ctx.actualParamList() != null)
			args = (List<Expr>)(List<?>)ctx.actualParamList().accept(this);
		
		return Arrays.asList(new MethodCall(new MethodCallExpr(recv, name, args)));
	}
	
	/**
	 * Visits Indent. Id could even be primitive type because of ambiguity of grammar.
	 * Returns either BoolCons, IntCons, Var or throws ParseFailure in case of integer
	 * is out of bounds.
	 */
	@Override 
	public List<Ast> visitIaIdent(JavaliParser.IaIdentContext ctx) { 
		String ident = ctx.Ident().getText();
		return parseIdent(ident, ctx.start.getLine());
	}
	
	/**
	 * Visits receiver. Visits parameters and
	 * returns MethodCallExpr object.
	 */
	@Override 
	public List<Ast> visitIaIaMethodCall(JavaliParser.IaIaMethodCallContext ctx) { 
		String name = ctx.Ident().getText();
		Expr recv = (Expr)ctx.identAccess().accept(this).get(0);
		
		List<Expr> args = new ArrayList<Expr>();
		
		if(ctx.actualParamList() != null)
			args = (List<Expr>)(List<?>)ctx.actualParamList().accept(this);
		
		return Arrays.asList(new MethodCallExpr(recv, name, args));
	}
	
	/**
	 * Receiver of method call is this object. Visits parameters and
	 * returns MethodCallExpr object.
	 */
	@Override 
	public List<Ast> visitIaMethodCall(JavaliParser.IaMethodCallContext ctx) { 
		String name = ctx.Ident().getText();
		Expr recv = new ThisRef();
		
		List<Expr> args = new ArrayList<Expr>();
		
		if(ctx.actualParamList() != null) {
			//@SuppressWarnings("unchecked")
			args = (List<Expr>)(List<?>)ctx.actualParamList().accept(this);
		}
		
		return Arrays.asList(new MethodCallExpr(recv, name, args));
	}
	
	/**
	 * Returns recursive Id. Visits recursively till terminal symbol found.
	 * Returns Field object.
	 */
	@Override 
	public List<Ast> visitIaIaIdent(JavaliParser.IaIaIdentContext ctx) { 
		Expr arg = (Expr)ctx.identAccess().accept(this).get(0);
		String name = ctx.Ident().getText();
		Field f = new Field(arg, name);
		return Arrays.asList(f);
	}
	
	/**
	 * Visits IdentAccess and index expression and returns Index object.
	 */
	@Override public List<Ast> visitIaArrayAccess(JavaliParser.IaArrayAccessContext ctx) { 
		Expr array = (Expr)ctx.identAccess().accept(this).get(0);
		Expr index = (Expr)ctx.expr().accept(this).get(0);
		return Arrays.asList(new Index(array, index));
	}
	
	/**
	 * Returns ThisRef object.
	 */
	@Override public List<Ast> visitIaThis(JavaliParser.IaThisContext ctx) { 
		return Arrays.asList(new ThisRef());
	}
	
	/**
	 * Visits Literal. Returns either BoolCons, IntCons,
	 * or throws ParseFailure in case of integer is out of bounds.
	 */
	@Override 
	public List<Ast> visitLIT(JavaliParser.LITContext ctx) { 
		String literal = ctx.Literal().getText();
		return parseIdent(literal, ctx.start.getLine());
	}
	
	/**
	 * Because of grammar ambiguity multiple rules can match literal and id.
	 * Method that finds out which of these it is. Creates appropriate object
	 * and checks for integer bounds.
	 */
	private List<Ast> parseIdent(String ident, int line){
		Ast id;
		switch(ident) {
			case "null": id = new NullConst();
				break;
			case "true": id = new BooleanConst(true);
				break;
			case "false": id = new BooleanConst(false);
				break;
			default:
				try {  
			         int parsed;
			         if(ident.startsWith("0x") || ident.startsWith("0X"))	
			        	 	parsed = Integer.parseInt(ident.substring(2), 16);
			         else
			        	 	parsed = Integer.parseInt(ident);
			         id = new IntConst(parsed);
			      } catch (NumberFormatException e) { 
			    	  		if(isNumeric(ident)) {
			    	  			throw new ParseFailure(line, "Integer out of bounds");
			    	  		}
			    	  	 id = new Var(ident);
			      } 
		}
		
		return Arrays.asList(id);	
	}
	
	// checks if given string is a number no matter the size.
	private boolean isNumeric(String str)
	{
		if(str.startsWith("0x") || str.startsWith("0X"))
			str = str.substring(2);
			
		if(str.startsWith("+") || str.startsWith("-"))
			str = str.substring(1);
		
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	/**
	 * Returns binary operator of + of -.
	 */
	@Override 
	public List<Ast> visitADDI(JavaliParser.ADDIContext ctx) { 
		Expr left = (Expr)ctx.expr(0).accept(this).get(0);
		Expr right = (Expr)ctx.expr(1).accept(this).get(0);
		BinaryOp.BOp op = BinaryOp.BOp.B_MINUS;
		String operator = ctx.getChild(1).getText();
		switch(operator) {
			case "+": op = BinaryOp.BOp.B_PLUS;
				break;
			case "-": op = BinaryOp.BOp.B_MINUS;
				break;
		}
		
		return Arrays.asList(new BinaryOp(left, op, right));
	}
	
	/**
	 * Returns logical and.
	 */
	@Override 
	public List<Ast> visitAND(JavaliParser.ANDContext ctx) { 
		Expr left = (Expr)ctx.expr(0).accept(this).get(0);
		Expr right = (Expr)ctx.expr(1).accept(this).get(0);
		BinaryOp.BOp op = BinaryOp.BOp.B_AND;
		
		return Arrays.asList(new BinaryOp(left, op, right));
	}
	
	/**
	 * Visits id access in expression.
	 */
	@Override 
	public List<Ast> visitTERM(JavaliParser.TERMContext ctx) { 
		return ctx.identAccess().accept(this);
	}
	
	/**
	 * Returns binary operation. Either equals or not equals.
	 */
	@Override 
	public List<Ast> visitEQI(JavaliParser.EQIContext ctx) { 
		Expr left = (Expr)ctx.expr(0).accept(this).get(0);
		Expr right = (Expr)ctx.expr(1).accept(this).get(0);
		BinaryOp.BOp op = BinaryOp.BOp.B_EQUAL;
		String operator = ctx.getChild(1).getText();
		switch(operator) {
			case "!=": op = BinaryOp.BOp.B_NOT_EQUAL;
				break;
			case "==": op = BinaryOp.BOp.B_EQUAL;
				break;
		}
		
		return Arrays.asList(new BinaryOp(left, op, right));
	}
	
	/**
	 * Returns unary operation(+,-,!)
	 */
	@Override 
	public List<Ast> visitUNARY(JavaliParser.UNARYContext ctx) { 
		Expr expr = (Expr)ctx.expr().accept(this).get(0);
		UnaryOp.UOp op = UnaryOp.UOp.U_BOOL_NOT;
		String operator = ctx.getChild(0).getText();
		switch(operator) {
			case "+": op = UnaryOp.UOp.U_PLUS;
				break;
			case "-": op = UnaryOp.UOp.U_MINUS;
				break;
			case "!": op = UnaryOp.UOp.U_BOOL_NOT;
				break;
			default: 
				System.out.println(operator + " not matched!");
		}
		
		return Arrays.asList(new UnaryOp(op, expr));
	}
	
	/**
	 * Returns binary operation(*,/,%)
	 */
	@Override 
	public List<Ast> visitMULTI(JavaliParser.MULTIContext ctx) { 
		Expr left = (Expr)ctx.expr(0).accept(this).get(0);
		Expr right = (Expr)ctx.expr(1).accept(this).get(0);
		BinaryOp.BOp op = BinaryOp.BOp.B_DIV;
		String operator = ctx.getChild(1).getText();
		switch(operator) {
		case "*": op = BinaryOp.BOp.B_TIMES;
			break;
		case "%": op = BinaryOp.BOp.B_MOD;
			break;
		case "/": op = BinaryOp.BOp.B_DIV;
			break;
		}
		
		return Arrays.asList(new BinaryOp(left, op, right));
	}
	
	/**
	 * Visits expression of cast and returns Cast object.
	 */
	@Override 
	public List<Ast> visitCAST(JavaliParser.CASTContext ctx) { 
		Expr expr = (Expr)ctx.expr().accept(this).get(0);
		String referenceType = ctx.referenceType().getText();
		
		return Arrays.asList(new Cast(expr, referenceType));
	}
	
	/**
	 * Returns binary operation of comparison(<,<=,>,>=)
	 */
	@Override 
	public List<Ast> visitCOMP(JavaliParser.COMPContext ctx) { 
		Expr left = (Expr)ctx.expr(0).accept(this).get(0);
		Expr right = (Expr)ctx.expr(1).accept(this).get(0);
		BinaryOp.BOp op = BinaryOp.BOp.B_LESS_THAN;
		String operator = ctx.getChild(1).toString();
		switch(operator) {
			case "<=": op = BinaryOp.BOp.B_LESS_OR_EQUAL;
				break;
			case ">=": op = BinaryOp.BOp.B_GREATER_OR_EQUAL;
				break;
			case ">": op = BinaryOp.BOp.B_GREATER_THAN;
				break;
			case "<": op = BinaryOp.BOp.B_LESS_THAN;
				break;
		}
		
		return Arrays.asList(new BinaryOp(left, op, right));
	}
	
	/**
	 * Visits bracket expression.
	 */
	@Override 
	public List<Ast> visitBRACKETS(JavaliParser.BRACKETSContext ctx) { 
		return ctx.expr().accept(this);
	}
	
	/**
	 * Returns logical or binary operand.
	 */
	@Override 
	public List<Ast> visitOR(JavaliParser.ORContext ctx) { 
		Expr left = (Expr)ctx.expr(0).accept(this).get(0);
		Expr right = (Expr)ctx.expr(1).accept(this).get(0);
		BinaryOp.BOp op = BinaryOp.BOp.B_OR;
		
		return Arrays.asList(new BinaryOp(left, op, right));
	}
}
