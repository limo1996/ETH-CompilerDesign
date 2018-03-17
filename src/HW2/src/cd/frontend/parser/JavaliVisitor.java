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
	 * Visit a parse tree produced by the {@code stmtAssign}
	 * labeled alternative in {@link JavaliParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtAssign(JavaliParser.StmtAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtMetCall}
	 * labeled alternative in {@link JavaliParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtMetCall(JavaliParser.StmtMetCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtIf}
	 * labeled alternative in {@link JavaliParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtIf(JavaliParser.StmtIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtWhile}
	 * labeled alternative in {@link JavaliParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtWhile(JavaliParser.StmtWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtReturn}
	 * labeled alternative in {@link JavaliParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtReturn(JavaliParser.StmtReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtWrite}
	 * labeled alternative in {@link JavaliParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtWrite(JavaliParser.StmtWriteContext ctx);
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
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link JavaliParser#assignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(JavaliParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignNew}
	 * labeled alternative in {@link JavaliParser#assignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignNew(JavaliParser.AssignNewContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignRead}
	 * labeled alternative in {@link JavaliParser#assignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignRead(JavaliParser.AssignReadContext ctx);
	/**
	 * Visit a parse tree produced by the {@code write}
	 * labeled alternative in {@link JavaliParser#writeStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrite(JavaliParser.WriteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code writeln}
	 * labeled alternative in {@link JavaliParser#writeStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteln(JavaliParser.WritelnContext ctx);
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
	 * Visit a parse tree produced by the {@code read}
	 * labeled alternative in {@link JavaliParser#readExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead(JavaliParser.ReadContext ctx);
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
	 * Visit a parse tree produced by the {@code CAST}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCAST(JavaliParser.CASTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code COMP}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCOMP(JavaliParser.COMPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BRACKETS}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBRACKETS(JavaliParser.BRACKETSContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OR}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOR(JavaliParser.ORContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LIT}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLIT(JavaliParser.LITContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ADDI}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitADDI(JavaliParser.ADDIContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AND}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAND(JavaliParser.ANDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TERM}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTERM(JavaliParser.TERMContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EQI}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEQI(JavaliParser.EQIContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UNARY}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUNARY(JavaliParser.UNARYContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MULTI}
	 * labeled alternative in {@link JavaliParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMULTI(JavaliParser.MULTIContext ctx);
}