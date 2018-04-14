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

public class SemanticAnalyzer {
	
	public final Main main;
	public Map<String, ClassDecl> classDeclarations;
	
	public SemanticAnalyzer(Main main) {
		this.main = main;
		classDeclarations = new HashMap<String, ClassDecl>();
	}
	
	/**
	 * Gets a class symbol according to specified name. 
	 * @param name of the class symbol to return.
	 * @return ClassSymbol if found in map or throws SemanticFaiure(NO_SUCH_TYPE) exception.
	 */
	public ClassSymbol getClassSymbol(String name) {
		if(name.equals("Object"))
			return ClassSymbol.objectType;
		/*else if (name.equals("null"))
			return ClassSymbol.nullType;*/
		else if(classDeclarations.containsKey(name))
			return classDeclarations.get(name).sym;
		else
			throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE);
	}
	
	public void check(List<ClassDecl> classDecls) throws SemanticFailure {
		for(ClassDecl cd : classDecls) {
			
			// check for double type declaration
			if(classDeclarations.containsKey(cd.name))
				throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
			
			cd.sym = new ClassSymbol(cd);
			classDeclarations.put(cd.name, cd);
		}
		
		SymbolVisitor visitor = new SymbolVisitor(this);
		for(ClassDecl cd : classDecls) {
			visitor.classDecl(cd, null);
		}
		
		// check for starting point
		// check for 'Main' class
		if(!classDeclarations.containsKey("Main"))
			throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT);
		else {
			
			ClassSymbol mc = classDeclarations.get("Main").sym;
			
			// check for 'main' method (in class 'Main')
			if(!mc.methods.containsKey("main"))
				throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT);
			else {
				
				MethodSymbol mm = mc.methods.get("main");
				
				// check for the right signature 'void main()'
				if(!mm.parameters.isEmpty() || !mm.returnType.equals(Symbol.PrimitiveTypeSymbol.voidType)) //!mm.returnType.name.equals("void"))
					throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT);
			}
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