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
	
	// Useful functions
	
	// returns 'true' iff type 'type1' is a subtype of type 'type2'
	public boolean isSubType(TypeSymbol type1, TypeSymbol type2) {
		ClassSymbol st;
		
		if(type1.equals(Symbol.PrimitiveTypeSymbol.voidType) || type2.equals(Symbol.PrimitiveTypeSymbol.voidType))
			return false;
		
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
	
	public boolean hasAReturn(Ast ast) {
		
		if(ast instanceof Ast.ReturnStmt)
			return true;
		
		if(ast instanceof Ast.Seq)
			for(Ast cs : ast.children())
				if(hasAReturn(cs))
					return true;
		
		if(ast instanceof Ast.IfElse)
			return hasAReturn(((Ast.IfElse)ast).then()) && hasAReturn(((Ast.IfElse)ast).otherwise());
		
		return false;
	}

	// Overrides
	
	public TypeSymbol builtInWrite(Ast.BuiltInWrite ast, ArrayList<Symbol> arg) {
		
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that the argument is of type 'int'
		if(!ast.arg().type.equals(PrimitiveTypeSymbol.intType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Argument for built-in function write is of type " + ast.arg().type.name + " instead of type int.");
		
		return null;
	}
	
	public TypeSymbol classDecl(Ast.ClassDecl ast, ArrayList<Symbol> arg) {
		
		arg.set(0, ast.sym);
		dflt(ast,arg);
		
		// check for Object type declared
		if(ast.sym.name.equals("Object"))
			throw new SemanticFailure(SemanticFailure.Cause.OBJECT_CLASS_DEFINED, "Class object has been defined.");
		
		// check for circular inheritance
		ClassSymbol cc = ast.sym.superClass;
		while(cc != null) {
			
			if(cc.equals(ast.sym))
				throw new SemanticFailure(SemanticFailure.Cause.CIRCULAR_INHERITANCE, "Circular inheritance involving class " + ast.sym.name + ".");
			
			cc = cc.superClass;
		}
		
		return null;
	}
	
	public TypeSymbol methodDecl(Ast.MethodDecl ast, ArrayList<Symbol> arg) {
		
		arg.set(1, ast.sym);
		dflt(ast,arg);
		
		ClassSymbol c = (ClassSymbol)arg.get(0);
		
		// check for double method declaration
		if(ast.sym != c.methods.get(ast.name))
			throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION, "Method " + c.name + " is declared twice in class " + c.name + ".");
		
		// check for double parameter declaration
		for(int i = 0; i < ast.sym.parameters.size(); i++)
			for(int j = i + 1; j < ast.sym.parameters.size(); j++)
				if(ast.sym.parameters.get(i).name.equals(ast.sym.parameters.get(j).name))
					throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION, "Method " + c.name + ":" + ast.sym.name + " has two parameters named " + ast.sym.parameters.get(i).name + ".");
		
		ClassSymbol cc = c.superClass;
		
		// check for (invalid) override
		while(cc != null) {
			
			if(cc.methods.containsKey(ast.name)) {
				
				// check return type
				if(!cc.methods.get(ast.sym.name).returnType.equals(ast.sym.returnType))
					throw new SemanticFailure(SemanticFailure.Cause.INVALID_OVERRIDE, "Method " + c.name + ":" + ast.sym.name + " overriding " + cc.name + ":" + ast.sym.name + " has return type " + ast.sym.returnType.name + " instead of expected return type " + cc.methods.get(ast.sym.name).returnType.name + ".");
				
				// check number of parameters
				if(ast.sym.parameters.size() != cc.methods.get(ast.sym.name).parameters.size())
					throw new SemanticFailure(SemanticFailure.Cause.INVALID_OVERRIDE, "Method " + c.name + ":" + ast.sym.name + " overriding " + cc.name + ":" + ast.sym.name + " has " + ast.sym.parameters.size() + " parameters instead of expected " + cc.methods.get(ast.sym.name).parameters.size() + ".");
				
				// check types of parameters
				for(int i = 0; i < ast.sym.parameters.size(); i++) {
					if(!ast.sym.parameters.get(i).type.equals(cc.methods.get(ast.sym.name).parameters.get(i).type))
						throw new SemanticFailure(SemanticFailure.Cause.INVALID_OVERRIDE, "Parameter " + Integer.toString(i) + " of method " + cc.name + ":" + ast.sym.name + " overriding " + cc.name + ":" + ast.sym.name + " has type " + ast.sym.parameters.get(i).type.name + " instead of expected " + cc.methods.get(ast.name).parameters.get(i).type.name + ".");
				}
				
				break;
			}
			
			cc = cc.superClass;
		}
		
		// check that there is a return statement on every possible control flow path
		if(!hasAReturn(ast.body()) && !ast.sym.returnType.equals(Symbol.PrimitiveTypeSymbol.voidType))
			throw new SemanticFailure(SemanticFailure.Cause.MISSING_RETURN, "Method " + c.name + ":" + ast.sym.name + " is missing a return statement.");
		
		return null;
	}
			
	public TypeSymbol varDecl(Ast.VarDecl ast, ArrayList<Symbol> arg) {
		
		dflt(ast,arg);
		ClassSymbol c = (ClassSymbol)arg.get(0);
		MethodSymbol m = (MethodSymbol)arg.get(1);
		
		// check for double declaration within fields of the class
		if(ast.sym.kind.equals(Symbol.VariableSymbol.Kind.FIELD)) {
			if(ast.sym != c.fields.get(ast.name)) 
				throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION, "Field " + c.name + ":" + ast.sym.name + " is declared twice.");
			}
		
		else {
			// check for double declaration within local variables
			if(ast.sym != m.locals.get(ast.name))
				throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION, "Local variable " + ast.sym.name + " in method " + c.name + ":" + m.name + "is declared twice.");
				
			// check for double declaration within parameters
			for (VariableSymbol parameter : m.parameters)
				if(ast.name.equals(parameter.name) && ast.sym != parameter)
					throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION, "Local variable " + ast.sym.name + " in method " + c.name + ":" + m.name + "is also a parameter of the method.");
		}
		
		return null;
	}

	public TypeSymbol assign(Ast.Assign ast, ArrayList<Symbol> arg) {
		
		ast.right().type = ast.right().accept(this, arg);
		ast.left().type = ast.left().accept(this, arg);
		
		// check that the left-hand side is assignable
		if(!((ast.left() instanceof Ast.Var) || (ast.left() instanceof Ast.Field) || (ast.left() instanceof Ast.Index)))
			throw new SemanticFailure(SemanticFailure.Cause.NOT_ASSIGNABLE, "Left-hand side of the assignment cannot be assigned.");
		
		// check that the type of the right-hand side is a subtype of the type of the left-hand side
		if(!isSubType(ast.right().type,ast.left().type))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Assignment types " + ast.left().type + " and " + ast.right().type + " do not match.");
		
		return null;
	}
	
	public TypeSymbol builtInWriteln(Ast.BuiltInWriteln ast, ArrayList<Symbol> arg) {
		return dflt(ast,arg);
	}
	
	public TypeSymbol ifElse(Ast.IfElse ast, ArrayList<Symbol> arg) {
		
		ast.condition().type = ast.condition().accept(this, arg);
		ast.then().accept(this, arg);
		ast.otherwise().accept(this, arg);
		
		// check that the condition has type 'boolean'
		if(!ast.condition().type.equals(PrimitiveTypeSymbol.booleanType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Condition has type " + ast.condition().type.name + " instead of expected type boolean.");
		
		return null;
	}
	
	public TypeSymbol returnStmt(Ast.ReturnStmt ast, ArrayList<Symbol> arg) {
		
		ClassSymbol c = (ClassSymbol)arg.get(0);
		MethodSymbol m = (MethodSymbol)arg.get(1);
		
		if(ast.arg() == null) {
			if(m.returnType.equals(Symbol.PrimitiveTypeSymbol.voidType))
				return null;
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Empty return for a method with return type different from void.");
		}
		
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that the argument to the return statement is of a type that is a subtype to the return type of the function
		if(m.returnType.equals(Symbol.PrimitiveTypeSymbol.voidType) || !(isSubType(ast.arg().type, m.returnType)))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Actual return type " + ast.arg().type.name + " does not match with formal return type " + m.returnType.name + " of method " + c.name + ":" + m.name + ".");
		
		return null;
	}
	
	public TypeSymbol methodCall(Ast.MethodCall ast, ArrayList<Symbol> arg) {
		return dflt(ast, arg);
	}
	
	public TypeSymbol nop(Ast.Nop ast, ArrayList<Symbol> arg) {
		return dflt(ast,arg);
	}
	
	public TypeSymbol seq(Ast.Seq ast, ArrayList<Symbol> arg) {
		return dflt(ast,arg);
	}
	
	public TypeSymbol whileLoop(Ast.WhileLoop ast, ArrayList<Symbol> arg) {
		
		ast.condition().type = ast.condition().accept(this, arg);
		ast.body().accept(this, arg);
		
		// check that the condition has type 'boolean'
		if(!ast.condition().type.equals(PrimitiveTypeSymbol.booleanType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Condition is of type " + ast.condition().type.name + " instead of expected type boolean.");
		
		return null;
	}
	
	public TypeSymbol binaryOp(Ast.BinaryOp ast, ArrayList<Symbol> arg) {
	
		ast.right().type = ast.right().accept(this, arg);
		ast.left().type = ast.left().accept(this, arg);
		
		// check that '==' and '!=' get types such that one is a subtype of the other and return a boolean
		if(ast.operator.equals(BOp.B_EQUAL) || ast.operator.equals(BOp.B_NOT_EQUAL)) {
			if(!(isSubType(ast.right().type,ast.left().type) || isSubType(ast.left().type,ast.right().type)))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "(In)Equality operator types " + ast.left().type.name + " and " + ast.right().type.name + "do not match.");
			ast.type = Symbol.PrimitiveTypeSymbol.booleanType;
		}
		
		// check that '||' and '&&' get two booleans and return a boolean
		else if(ast.operator.equals(BOp.B_OR) || ast.operator.equals(BOp.B_AND)) {
			if(!(ast.right().type.equals(Symbol.PrimitiveTypeSymbol.booleanType) && ast.left().type.equals(Symbol.PrimitiveTypeSymbol.booleanType)))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "At least one of the AND/OR operator types " + ast.left().type.name + " and " + ast.right().type.name + "is not boolean.");
			ast.type = Symbol.PrimitiveTypeSymbol.booleanType;
			}
		
			// check that all other binary operators get two integers and return the appropriate type (boolean/integer)
			else {
				if(!(ast.right().type.equals(Symbol.PrimitiveTypeSymbol.intType) && ast.left().type.equals(Symbol.PrimitiveTypeSymbol.intType)))
					throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "One of the operator types " + ast.left().type.name + " and " + ast.right().type.name + " for an arithmetic/relational operand is not int.");
				if(ast.operator.equals(BOp.B_GREATER_OR_EQUAL) || ast.operator.equals(BOp.B_GREATER_THAN) || ast.operator.equals(BOp.B_LESS_OR_EQUAL) || ast.operator.equals(BOp.B_LESS_THAN))
					ast.type = Symbol.PrimitiveTypeSymbol.booleanType;
				else
					ast.type = Symbol.PrimitiveTypeSymbol.intType;
			}
		
		return ast.type;
	}

	public TypeSymbol booleanConst(Ast.BooleanConst ast, ArrayList<Symbol> arg) {
		
		ast.type = Symbol.PrimitiveTypeSymbol.booleanType;
		
		return ast.type;
	}
	
	public TypeSymbol builtInRead(Ast.BuiltInRead ast, ArrayList<Symbol> arg) {
		
		ast.type = Symbol.PrimitiveTypeSymbol.intType;
		
		return ast.type;
	}
		
	public TypeSymbol cast(Ast.Cast ast, ArrayList<Symbol> arg) {
		
		ast.type = analyzer.getType(ast.typeName);
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that either the cast type is a subtype of the original type or vice versa and return an object of the cast type
		if(!(isSubType(ast.type,ast.arg().type) || isSubType(ast.arg().type,ast.type)))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Cast type " + ast.type.name + " and argument type " + ast.arg().type.name + "do not match.");
		
		return ast.type;
	}
	
	public TypeSymbol field(Ast.Field ast, ArrayList<Symbol> arg) {
		
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that the argument has a class-type
		if(!(ast.arg().type instanceof Symbol.ClassSymbol))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Caller has non-class type " + ast.arg().type.name + ".");
		
		ClassSymbol cc = (ClassSymbol)ast.arg().type;
		
		while(cc != null && !cc.fields.containsKey(ast.fieldName))
			cc = cc.superClass;
		
		// check that the field exists in the class of the calling object or a superclass of it
		if(cc == null)
			throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_FIELD, "There is not field " + ast.fieldName + " in the class " + ast.arg().type.name + " or in any of its superclasses.");
		
		ast.sym = cc.getField(ast.fieldName);
		ast.type = ast.sym.type;
		
		return ast.type;
	}

	public TypeSymbol index(Ast.Index ast, ArrayList<Symbol> arg) {
		
		ast.left().type = ast.left().accept(this, arg);
		ast.right().type = ast.right().accept(this, arg);
		
		// check that the index is of type 'int'
		if(!ast.right().type.equals(Symbol.PrimitiveTypeSymbol.intType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Index of array indexing expression has type " + ast.right().type.name + "instead of int.");
		
		// check that the array is of an array type
		if(!(ast.left().type instanceof Symbol.ArrayTypeSymbol))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Array of array indexing expression has non-array type " + ast.left().type.name + ".");
	
		ast.type = analyzer.getType(ast.left().type.name.substring(0, ast.left().type.name.length()-2));
		
		return ast.type;
	}

	public TypeSymbol intConst(Ast.IntConst ast, ArrayList<Symbol> arg) {
		
		ast.type = Symbol.PrimitiveTypeSymbol.intType;
		
		return ast.type;
	}
		
	public TypeSymbol methodCall(Ast.MethodCallExpr ast, ArrayList<Symbol> arg) {
		
		ast.receiver().type = ast.receiver().accept(this, arg);
		
		// check that the receiver is of a class-type
		if(!(ast.receiver().type instanceof Symbol.ClassSymbol))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Caller of method has non-class type " + ast.receiver().type.name + ".");
		
		ClassSymbol cc = (ClassSymbol)ast.receiver().type;

		while(cc != null && !cc.methods.containsKey(ast.methodName))
			cc = cc.superClass;
		
		// check that the method exists in the class of the calling object or a superclass of it
		if(cc == null)
			throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_METHOD, "There is not method " + ast.methodName + " in the class " + ast.receiver().type.name + " or in any of its superclasses.");
		
		ast.sym = cc.getMethod(ast.methodName);
		ast.type = ast.sym.returnType;
		
		// check that there are as many arguments passed as there are formal parameters in the declaration
		if(ast.argumentsWithoutReceiver().size() != ast.sym.parameters.size())
			throw new SemanticFailure(SemanticFailure.Cause.WRONG_NUMBER_OF_ARGUMENTS, Integer.toString(ast.argumentsWithoutReceiver().size()) + " arguments have been passed to the method " + ast.receiver().type.name + ":" + ast.type.name + " instead of the expected " + Integer.toString(ast.sym.parameters.size()) + ".");
		
		for(int i = 0; i < ast.argumentsWithoutReceiver().size(); i++) {
			ast.argumentsWithoutReceiver().get(i).type = ast.argumentsWithoutReceiver().get(i).accept(this, arg);
		
			// check that each actual argument has the type of the formal one
			if(!isSubType(ast.argumentsWithoutReceiver().get(i).type,ast.sym.parameters.get(i).type))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Actual type " + ast.argumentsWithoutReceiver().get(i).type.name + " of parameter " + Integer.toString(i) + " of call of method " + ast.receiver().type.name + ":" + ast.sym.name + " does not match formal type" + ast.sym.parameters.get(i).type.name + ".");
		}
		
		return ast.sym.returnType;
	}

	public TypeSymbol newObject(Ast.NewObject ast, ArrayList<Symbol> arg) {
		
		ast.type = analyzer.getType(ast.typeName);
		
		return ast.type;
	}

	public TypeSymbol newArray(Ast.NewArray ast, ArrayList<Symbol> arg) {

		ast.type = analyzer.getType(ast.typeName);
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that array size is of integer type
		if(!ast.arg().type.equals(Symbol.PrimitiveTypeSymbol.intType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Argument of NewArray expression has type " + ast.arg().type.name + " instead of int.");
		
		return ast.type;
	}
	
	public TypeSymbol nullConst(Ast.NullConst ast, ArrayList<Symbol> arg) {
		
		ast.type = Symbol.ClassSymbol.nullType;
		
		return ast.type;
	}

	public TypeSymbol thisRef(Ast.ThisRef ast, ArrayList<Symbol> arg) {
		
		ast.type = (ClassSymbol)arg.get(0);
		
		return ast.type;
	}

	public TypeSymbol unaryOp(Ast.UnaryOp ast, ArrayList<Symbol> arg) {
		
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that '!' gets a boolean and return a boolean
		if(ast.operator.equals(UOp.U_BOOL_NOT)) {
			if(!ast.arg().type.equals(Symbol.PrimitiveTypeSymbol.booleanType))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Operand of not (!) operator has type " + ast.arg().type.name + " instead of boolean.");
			return Symbol.PrimitiveTypeSymbol.booleanType;
		}
		// check that unary '+' and '-' get a integer and return an integer
		else {
			if(!ast.arg().type.equals(Symbol.PrimitiveTypeSymbol.intType))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Operand of +/- operator has type " + ast.arg().type.name + " instead of int.");
			return Symbol.PrimitiveTypeSymbol.intType;
		}
	}

	public TypeSymbol var(Ast.Var ast, ArrayList<Symbol> arg) {
		
		ClassSymbol c = (ClassSymbol)arg.get(0);
		MethodSymbol m = (MethodSymbol)arg.get(1);
		
		ClassSymbol cc = c;
		
		while(cc != null && !cc.fields.containsKey(ast.name)) 
			cc = cc.superClass;
		
		if(cc != null)
			ast.sym = cc.getField(ast.name);
		
		for(int i = 0; i < m.parameters.size(); i++)
			if(m.parameters.get(i).name.equals(ast.name))
				ast.sym = m.parameters.get(i);
		
		if(m.locals.containsKey(ast.name))
			ast.sym = m.locals.get(ast.name);
		
		// check that the variable exists (was declared)
		if(ast.sym == null)
			throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_VARIABLE, "There is no variable " + ast.name + " in the scope.");
		
		ast.type = ast.sym.type;
		
		return ast.type;
	}
}