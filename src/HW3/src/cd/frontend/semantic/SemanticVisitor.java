package cd.frontend.semantic;

import cd.ir.*;
import cd.ir.Symbol.*;
import cd.ir.Ast.*;
import cd.ir.Ast.BinaryOp.BOp;
import cd.ir.Ast.UnaryOp.UOp;

import java.util.ArrayList;
import java.util.List;

public class SemanticVisitor extends AstVisitor<TypeSymbol, ArrayList<Symbol>> {
	
	private final SemanticAnalyzer analyzer;

	public SemanticVisitor(SemanticAnalyzer analyzer) {
		this.analyzer = analyzer;
	}
	
	// returns 'true' iff type 'type1' is a subtype of type 'type2'
	public boolean isSubType(TypeSymbol type1, TypeSymbol type2) {
		ClassSymbol st;
		
		if(type1.equals(type2))
			return true;
		
		if(type1 instanceof Symbol.ArrayTypeSymbol && type2.equals(Symbol.ClassSymbol.objectType))
			return true;
		
		if(type1.equals(Symbol.ClassSymbol.nullType) && type2.isReferenceType())
			return true;
		
		if(type1 instanceof Symbol.ClassSymbol) {
			st = (ClassSymbol)type1;
			while(st.superClass != null) {
				st = st.superClass;
				if(type2.equals(st))
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * TODO:
	 * - replace method bodies and check for semantic errors in them.
	 * - don't forget to visit their children
	 * - get inspired by previous homeworks
	 * - list of semantic errors is in javali specification on pages 8 and 9
	 * - good luck :)
	 */

	public TypeSymbol builtInWrite(Ast.BuiltInWrite ast, ArrayList<Symbol> arg) {
		
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that the argument is of type 'int'
		if(!ast.arg().type.equals(PrimitiveTypeSymbol.intType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
		
		return null;
	}
	
	public TypeSymbol classDecl(Ast.ClassDecl ast, ArrayList<Symbol> arg) {
		
		arg.set(0, ast.sym);
		dflt(ast,arg);
		
		// check for Object type declared
		if(ast.name.equals("Object"))
			throw new SemanticFailure(SemanticFailure.Cause.OBJECT_CLASS_DEFINED);
		
		return null;
	}
	
	public TypeSymbol methodDecl(Ast.MethodDecl ast, ArrayList<Symbol> arg) {
		
		arg.set(1, ast.sym);
		dflt(ast,arg);
		
		ClassSymbol c = (ClassSymbol)arg.get(0);
		
		// check for double method declaration
		if(ast.sym != c.methods.get(ast.name))
			throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
		
		// check for (invalid) override
		if(c.superClass.methods.containsKey(ast.name)) {
			
			MethodSymbol sm = c.superClass.methods.get(ast.name);
			
			// check return type
			if(!sm.returnType.equals(ast.sym.returnType))
				throw new SemanticFailure(SemanticFailure.Cause.INVALID_OVERRIDE);
			
			// check number of parameters
			if(ast.sym.parameters.size() != sm.parameters.size())
				throw new SemanticFailure(SemanticFailure.Cause.INVALID_OVERRIDE);
			
			// check types of parameters
			for(int i = 0; i < ast.sym.parameters.size(); i++) {
				if(ast.sym.parameters.get(i).type.equals(sm.parameters.get(i).type))
					throw new SemanticFailure(SemanticFailure.Cause.INVALID_OVERRIDE);
			}
		}
		
		return null;
	}
			
	public TypeSymbol varDecl(Ast.VarDecl ast, ArrayList<Symbol> arg) {
		
		ClassSymbol c = (ClassSymbol)arg.get(0);
		MethodSymbol m = (MethodSymbol)arg.get(1);
		dflt(ast,arg);
		
		// check for double variable declaration
		if(ast.sym.kind.equals(Symbol.VariableSymbol.Kind.FIELD)) {
				
		// check for double variable declaration within fields of class
			if(ast.sym != c.fields.get(ast.name))
				throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
		}
		else {
			
			// check for double declaration within local variables
			if(ast.sym != m.locals.get(ast.name))
				throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
			
			// check for double declaration within parameters
			for (VariableSymbol parameter : m.parameters) {
				if(ast.name.equals(parameter.name) && ast.sym != parameter)
					throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
			}
		}
		return null;
	}

	public TypeSymbol assign(Ast.Assign ast, ArrayList<Symbol> arg) {
		
		ast.right().type = ast.right().accept(this, arg);
		ast.left().type = ast.left().accept(this, arg);
		
		// check that the type of the right-hand side is a subtype of the type of the left-hand side
		if(!isSubType(ast.right().type,ast.left().type))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
		
		return null;
	}
	
	public TypeSymbol builtInWriteln(Ast.BuiltInWriteln ast, ArrayList<Symbol> arg) {
		return dflt(ast,arg);
	}
	
	public TypeSymbol ifElse(Ast.IfElse ast, ArrayList<Symbol> arg) {
		
		ast.condition().type = ast.condition().accept(this, arg);
		dflt(ast,arg);
		
		// check that the condition has type 'boolean'
		if(!ast.condition().type.equals(PrimitiveTypeSymbol.booleanType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
		
		return null;
	}
	
	public TypeSymbol returnStmt(Ast.ReturnStmt ast, ArrayList<Symbol> arg) {
		return dflt(ast, arg);
	}
	
	public TypeSymbol methodCall(Ast.MethodCall ast, ArrayList<Symbol> arg) {
		return dflt(ast, arg);
	}
	
	public TypeSymbol nop(Ast.Nop ast, ArrayList<Symbol> arg) {
		return dflt(ast, arg);
	}
	
	public TypeSymbol seq(Ast.Seq ast, ArrayList<Symbol> arg) {
		return dflt(ast, arg);
	}
	
	public TypeSymbol whileLoop(Ast.WhileLoop ast, ArrayList<Symbol> arg) {
		
		dflt(ast,arg);
		ast.condition().type = ast.condition().accept(this, arg);

		// check that the condition has type 'boolean'
		if(!ast.condition().type.equals(PrimitiveTypeSymbol.booleanType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
		
		return null;
	}
	
	public TypeSymbol binaryOp(Ast.BinaryOp ast, ArrayList<Symbol> arg) {
		
		ast.right().type = ast.right().accept(this, arg);
		ast.left().type = ast.left().accept(this, arg);
		
		// check that '==' and '!=' get types such that one is a subtype of the other and return a boolean
		if(ast.operator.equals(BOp.B_EQUAL) || ast.operator.equals(BOp.B_NOT_EQUAL)) {
			if(!(isSubType(ast.right().type,ast.left().type) || isSubType(ast.left().type,ast.right().type)))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
			return Symbol.PrimitiveTypeSymbol.booleanType;
		}
		// check that '||' and '&&' get two booleans and return a boolean
		else if(ast.operator.equals(BOp.B_OR) || ast.operator.equals(BOp.B_AND)) {
			if(!(ast.right().type.equals(Symbol.PrimitiveTypeSymbol.booleanType) && ast.left().type.equals(Symbol.PrimitiveTypeSymbol.booleanType)))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
			return Symbol.PrimitiveTypeSymbol.booleanType;
		}
		// check that all other binary operators get two integers and return the appropriate type (boolean/integer)
		else {
			if(!(ast.right().type.equals(Symbol.PrimitiveTypeSymbol.intType) && ast.left().type.equals(Symbol.PrimitiveTypeSymbol.intType)))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
			if(ast.operator.equals(BOp.B_GREATER_OR_EQUAL) || ast.operator.equals(BOp.B_GREATER_THAN) || ast.operator.equals(BOp.B_LESS_OR_EQUAL) || ast.operator.equals(BOp.B_LESS_THAN))
				return Symbol.PrimitiveTypeSymbol.booleanType;
			return Symbol.PrimitiveTypeSymbol.intType;
		}

	}

	public TypeSymbol booleanConst(Ast.BooleanConst ast, ArrayList<Symbol> arg) {
		return Symbol.PrimitiveTypeSymbol.booleanType;
	}
	
	public TypeSymbol builtInRead(Ast.BuiltInRead ast, ArrayList<Symbol> arg) {
		return Symbol.PrimitiveTypeSymbol.intType;
	}
		
	public TypeSymbol cast(Ast.Cast ast, ArrayList<Symbol> arg) {
		
		ClassSymbol ct = analyzer.getClassSymbol(ast.typeName);
		dflt(ast,arg);
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that either the cast type is a subtype of the original type or vice versa and return an object of the cast type
		if(!(isSubType(ct,ast.arg().type) || isSubType(ast.arg().type,ct)))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
		
		return ct;
	}
	
	public TypeSymbol field(Ast.Field ast, ArrayList<Symbol> arg) {
		return dflt(ast, arg);
	}

	public TypeSymbol index(Ast.Index ast, ArrayList<Symbol> arg) {
		
		dflt(ast,arg);
		ast.left().type = ast.left().accept(this, arg);
		ast.right().type = ast.right().accept(this, arg);
		
		// check that the index is of type 'int'
		if(!ast.right().type.equals(Symbol.PrimitiveTypeSymbol.intType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
		
		// check that the array is of an array type
		if(!(ast.left().type instanceof Symbol.ArrayTypeSymbol))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
		
		return analyzer.getClassSymbol(ast.left().type.name.substring(0, ast.left().type.name.length()-2));
	}

	public TypeSymbol intConst(Ast.IntConst ast, ArrayList<Symbol> arg) {
		return Symbol.PrimitiveTypeSymbol.intType;
	}
		
	public TypeSymbol methodCall(Ast.MethodCallExpr ast, ArrayList<Symbol> arg) {
		return dflt(ast,arg);
	}

	public TypeSymbol newObject(Ast.NewObject ast, ArrayList<Symbol> arg) {
		
		ast.type = analyzer.getClassSymbol(ast.typeName);
		
		return ast.type;
	}

	public TypeSymbol newArray(Ast.NewArray ast, ArrayList<Symbol> arg) {
		return dflt(ast, arg);
	}
	
	public TypeSymbol nullConst(Ast.NullConst ast, ArrayList<Symbol> arg) {
		return Symbol.ClassSymbol.nullType;
	}

	public TypeSymbol thisRef(Ast.ThisRef ast, ArrayList<Symbol> arg) {
		return (ClassSymbol)arg.get(0);
	}

	public TypeSymbol unaryOp(Ast.UnaryOp ast, ArrayList<Symbol> arg) {
		
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that '!' gets a boolean and return a boolean
		if(ast.operator.equals(UOp.U_BOOL_NOT)) {
			if(!ast.arg().type.equals(Symbol.PrimitiveTypeSymbol.booleanType))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
			return Symbol.PrimitiveTypeSymbol.booleanType;
		}
		// check that unary '+' and '-' get a integer and return an integer
		else {
			if(!ast.arg().type.equals(Symbol.PrimitiveTypeSymbol.intType))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
			return Symbol.PrimitiveTypeSymbol.intType;
		}
	}

	public TypeSymbol var(Ast.Var ast, ArrayList<Symbol> arg) {
		return dflt(ast, arg);
	}
}
