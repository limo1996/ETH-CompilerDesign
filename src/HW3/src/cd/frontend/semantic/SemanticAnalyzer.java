package cd.frontend.semantic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import cd.Main;
import cd.ir.Ast.ClassDecl;
import cd.ir.Symbol;
import cd.ir.Symbol.ClassSymbol;
import cd.ir.Symbol.MethodSymbol;
import cd.ir.Symbol.PrimitiveTypeSymbol;
import cd.ir.Symbol.TypeSymbol;

public class SemanticAnalyzer {
	
	public final Main main;
	public Map<String, ClassDecl> classDeclarations;
	public Map<String, Symbol.ArrayTypeSymbol> arrayTypes;
	
	public SemanticAnalyzer(Main main) {
		this.main = main;
		classDeclarations = new HashMap<String, ClassDecl>();
		arrayTypes = new HashMap<String, Symbol.ArrayTypeSymbol>();
		arrayTypes.put("int", new Symbol.ArrayTypeSymbol(Symbol.PrimitiveTypeSymbol.intType));
		arrayTypes.put("boolean", new Symbol.ArrayTypeSymbol(Symbol.PrimitiveTypeSymbol.booleanType));
		arrayTypes.put("void", new Symbol.ArrayTypeSymbol(Symbol.PrimitiveTypeSymbol.voidType));
		arrayTypes.put("Object", new Symbol.ArrayTypeSymbol(Symbol.ClassSymbol.objectType));
		arrayTypes.put("null", new Symbol.ArrayTypeSymbol(Symbol.ClassSymbol.nullType));
	}
	
	/**
	 * Gets a class symbol according to specified name. 
	 * @param name of the class symbol to return.
	 * @return ClassSymbol if found in map or throws SemanticFaiure(NO_SUCH_TYPE) exception.
	 */
	public Symbol.TypeSymbol getType(String name) {
		
		switch(name) {
		
			case "int": return Symbol.PrimitiveTypeSymbol.intType;
					
			case "boolean": return Symbol.PrimitiveTypeSymbol.booleanType;
			
			case "void": return Symbol.PrimitiveTypeSymbol.voidType;
			
			case "Object": return Symbol.ClassSymbol.objectType;
			
			case "null": return Symbol.ClassSymbol.nullType;
			
			default: {
				if(name.endsWith("[]"))
					return arrayTypes.get(name.substring(0, name.length()-2));
				
				if(classDeclarations.containsKey(name))
					return classDeclarations.get(name).sym;
				
				throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE, "There is no type " + name + ".");
			}
		}
	}
	
	public void check(List<ClassDecl> classDecls) throws SemanticFailure {
		
		for(ClassDecl cd : classDecls) {
			
			// check for double type declaration
			if(classDeclarations.containsKey(cd.name))
				throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION, "Class " + cd.name + " is declared twice.");
			
			cd.sym = new ClassSymbol(cd);
			classDeclarations.put(cd.name, cd);
			arrayTypes.put(cd.name, new Symbol.ArrayTypeSymbol(cd.sym));
		}
		
		SymbolVisitor symbolVisitor = new SymbolVisitor(this);
		for(ClassDecl cd : classDecls) {
			symbolVisitor.classDecl(cd, null);
		}
		
		// check for starting point
		// check for 'Main' class
		if(!classDeclarations.containsKey("Main"))
			throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT, "Main class missing.");
		else {
			
			ClassSymbol cc = classDeclarations.get("Main").sym;
			
			// check for 'main' method (in class 'Main')
			while(cc != null && !cc.methods.containsKey("main"))
				cc = cc.superClass;
			
			if(cc == null)
				throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT, "main method missing in class Main.");
			
			MethodSymbol mm = cc.methods.get("main");
				
			// check for the right signature 'void main()'
			if(!mm.parameters.isEmpty() || !mm.returnType.equals(Symbol.PrimitiveTypeSymbol.voidType))
				throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT, "main method in class Main does not have the right signature.");
			
		}
		
		SemanticVisitor semanticVisitor = new SemanticVisitor(this);
		ArrayList<Symbol> ss = new ArrayList<Symbol>(2);
		ss.add(null);
		ss.add(null);
		for(ClassDecl cd : classDecls) {
			cd.accept(semanticVisitor, ss);
		}
	}
}