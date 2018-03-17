package cd.frontend.parser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

import cd.ToDoException;
import cd.frontend.parser.JavaliParser.*;
import cd.ir.Ast.*;
import cd.ir.Ast;

public final class JavaliAstVisitor extends JavaliBaseVisitor<Ast> {

	public List<ClassDecl> classDecls = new ArrayList<>();
	
	/*
	 * Processes class declaration node, adds it to private list of classes and returns null
	 */
	@Override
	public Ast visitClassDecl(ClassDeclContext ctx) {
		List<Ast> children = new ArrayList<Ast>();
		for(ParseTree prctx : ctx.children) {
			children.add(prctx.accept(this));
		}
		String superClass = "Object";
		if(ctx.Ident(1) != null)
			superClass = ctx.Ident(1).getText();
		ClassDecl cd = new ClassDecl(ctx.Ident(0).getText(), superClass, children);
		return null;
	}
	
	
}
