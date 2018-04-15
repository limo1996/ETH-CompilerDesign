package cd.frontend.semantic;

import java.util.ArrayList;
import java.util.List;

import cd.ir.*;
import cd.ir.Symbol.*;
import cd.ir.Ast.*;

public class SymbolVisitor extends AstVisitor<Symbol, VariableSymbol.Kind> {
	
	private final SemanticAnalyzer analyzer;
	
	public SymbolVisitor(SemanticAnalyzer analyzer) {
		this.analyzer = analyzer;
	}
	
	/**
	 * Visits class declaration and all its field and class declarations.
	 * Returns ClassSymbol with obtained method and field symbols.
	 */
	public Symbol classDecl(ClassDecl ast, VariableSymbol.Kind arg) {
		
		for(MethodDecl md : ast.childrenOfType(MethodDecl.class)) {
			 MethodSymbol ms =  (MethodSymbol)md.accept(this, VariableSymbol.Kind.PARAM);
			 ast.sym.methods.put(ms.name, ms);
		}
		
		for(VarDecl vd : ast.childrenOfType(VarDecl.class)) {
			VariableSymbol vs = (VariableSymbol)vd.accept(this, VariableSymbol.Kind.FIELD);
			ast.sym.fields.put(vs.name, vs);
		}
		
		if(ast.superClass != null) {
			ast.sym.superClass = (ClassSymbol)analyzer.getType(ast.superClass);
		}	
		
		return ast.sym;
	}
	
	/**
	 * Visits Method declaration and all its variable declarations. 
	 * Returns MethodSymbol with obtained parameters and locals.
	 */
	public Symbol methodDecl(MethodDecl ast, VariableSymbol.Kind arg) {
		
		MethodSymbol ms = new MethodSymbol(ast);
		
		for(int i = 0; i < ast.argumentNames.size(); i++) {
			VariableSymbol vs = new VariableSymbol(ast.argumentNames.get(i),analyzer.getType(ast.argumentTypes.get(i)),VariableSymbol.Kind.PARAM);
			ms.parameters.add(vs);
		}
		
		for(VarDecl vd : ast.decls().childrenOfType(VarDecl.class)) {
			VariableSymbol vs = (VariableSymbol)vd.accept(this, VariableSymbol.Kind.LOCAL);
			ms.locals.put(vs.name, vs);
		}
		
		ms.returnType = analyzer.getType(ast.returnType);
		ast.sym = ms;
		return ms;
	}
		
	/**
	 * Visits variable declaration. 
	 * Returns VariableSymbol with kind passed as parameter and name, type taken from ast node.
	 */
	public Symbol varDecl(VarDecl ast, VariableSymbol.Kind arg) {
		VariableSymbol vs = new VariableSymbol(ast.name, analyzer.getType(ast.type), arg);
		ast.sym = vs;
		return vs;
	}
}