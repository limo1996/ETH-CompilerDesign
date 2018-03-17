// Generated from /Users/limo/Documents/eth-courses/CompilerDesign/homework/src/HW2/src/cd/frontend/parser/Javali.g4 by ANTLR 4.7.1

	// Java header
	package cd.frontend.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JavaliParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JavaliVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JavaliParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(JavaliParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(JavaliParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#referenceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceType(JavaliParser.ReferenceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(JavaliParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnit(JavaliParser.UnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#classDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDecl(JavaliParser.ClassDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#memberList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberList(JavaliParser.MemberListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(JavaliParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#methodDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDecl(JavaliParser.MethodDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#formalParamList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParamList(JavaliParser.FormalParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(JavaliParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#stmtBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtBlock(JavaliParser.StmtBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#methodCallStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallStmt(JavaliParser.MethodCallStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#assignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStmt(JavaliParser.AssignmentStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#writeStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteStmt(JavaliParser.WriteStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(JavaliParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(JavaliParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(JavaliParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#newExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpr(JavaliParser.NewExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#readExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadExpr(JavaliParser.ReadExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#actualParamList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitActualParamList(JavaliParser.ActualParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#identAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentAccess(JavaliParser.IdentAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(JavaliParser.ExprContext ctx);
}