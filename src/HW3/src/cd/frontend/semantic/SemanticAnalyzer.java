package cd.frontend.semantic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cd.Main;
import cd.ir.Ast.ClassDecl;
import cd.ir.Symbol.ClassSymbol;

public class SemanticAnalyzer {
	
	public final Main main;
	public Map<String, ClassDecl> classDeclarations;
	public boolean start_point_defined;
	
	public SemanticAnalyzer(Main main) {
		this.main = main;
		classDeclarations = new HashMap<String, ClassDecl>();
		start_point_defined = false;
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
			cd.sym = new ClassSymbol(cd);
			classDeclarations.put(cd.name, cd);
		}
		
		SymbolVisitor visitor = new SymbolVisitor(this);
		for(ClassDecl cd : classDecls) {
			visitor.classDecl(cd, null);
		}
		if(!start_point_defined)
			throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT);
	}

}
