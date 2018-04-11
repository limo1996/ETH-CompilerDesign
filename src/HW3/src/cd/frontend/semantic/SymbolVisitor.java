package cd.frontend.semantic;

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
		if(ast.superClass != null && ast.superClass != "") {
			ast.sym.superClass = this.analyzer.getClassSymbol(ast.superClass);
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
			ms.parameters.add(this.getType(ast.argumentTypes.get(i),
					ast.argumentNames.get(i), VariableSymbol.Kind.PARAM));
		}
		
		for(VarDecl vd : ast.decls().childrenOfType(VarDecl.class)) {
			VariableSymbol vs = (VariableSymbol)vd.accept(this, VariableSymbol.Kind.LOCAL);
			ms.locals.put(vs.name, vs);
		}
		
		if(ast.name.equals("main") && ast.argumentNames.size() == 0 && ast.returnType.equals("void"))
			this.analyzer.start_point_defined = true;

		ms.returnType = this.getTypeSymbol(ast.returnType);
		ast.sym = ms;
		return ms;
	}
	
	// creates variable symbol from arguments
	private VariableSymbol getType(String type, String name, VariableSymbol.Kind kind) {
		return new VariableSymbol(name, this.getTypeSymbol(type), kind);
	}
	
	// parses type symbol out of string
	private TypeSymbol getTypeSymbol(String type) {
		TypeSymbol ts;
		boolean is_array = false;
		if(type.endsWith("[]")) {
			type = type.substring(0, type.indexOf("[]"));
			is_array = true;
		}
		
		if(type.equals("void") || type.equals("boolean") || type.equals("int"))
			ts = new PrimitiveTypeSymbol(type);
		else 
			ts = this.analyzer.getClassSymbol(type);
		
		if(is_array)
			ts = new ArrayTypeSymbol(ts);
		
		return ts;
	}
		
	/**
	 * Visits variable declaration. 
	 * Returns VariableSymbol with kind passed as parameter and name, type taken from ast node.
	 */
	public Symbol varDecl(VarDecl ast, VariableSymbol.Kind arg) {
		VariableSymbol vs = getType(ast.type, ast.name, arg);
		ast.sym = vs;
		return vs;
	}
}
