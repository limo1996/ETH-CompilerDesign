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
		
		if(type1.equals(Symbol.PrimitiveTypeSymbol.voidType) || type2.equals(Symbol.PrimitiveTypeSymbol.voidType))
			return false;
		
		if(type1.equals(type2))
			return true;
		
		if(type1 instanceof Symbol.ArrayTypeSymbol) {
			if(type2.equals(Symbol.ClassSymbol.objectType))
					return true;
			
			if(type2 instanceof Symbol.ArrayTypeSymbol) {
				return type1.name.equals(type2.name);
			}
		}
			
		
		
		if(type1.equals(Symbol.ClassSymbol.nullType) && type2.isReferenceType())
			return true;
		
		if(type1 instanceof Symbol.ClassSymbol) {
			List<ClassSymbol> sts = new ArrayList();
			st = (ClassSymbol)type1;
			while(st.superClass != null) {
				if(sts.contains(st))
					return false;
				else {
				sts.add(st);
				st = st.superClass;
				if(type2.equals(st)) 
					return true;
				}
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
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Write arg not int");
		
		return null;
	}
	
	public TypeSymbol classDecl(Ast.ClassDecl ast, ArrayList<Symbol> arg) {
		
		arg.set(0, ast.sym);
		dflt(ast,arg);
		
		// check for Object type declared
		if(ast.name.equals("Object"))
			throw new SemanticFailure(SemanticFailure.Cause.OBJECT_CLASS_DEFINED);
		
		// check for circular inheritance
		ClassSymbol cc = ast.sym.superClass;
		while(cc != null) {
			if(cc.equals(ast.sym))
				throw new SemanticFailure(SemanticFailure.Cause.CIRCULAR_INHERITANCE);
			
			cc = cc.superClass;
		}
		
		/*String actual = ast.superClass;
		while(actual != null && !actual.equals("") && !actual.equals("Object")) {
			if(actual.equals(ast.name))
				throw new SemanticFailure(SemanticFailure.Cause.CIRCULAR_INHERITANCE);
			
			actual = analyzer.getClassSymbol(actual).ast.superClass;
		}*/
		
		return null;
	}
	
	public TypeSymbol methodDecl(Ast.MethodDecl ast, ArrayList<Symbol> arg) {
		
		arg.set(1, ast.sym);
		dflt(ast,arg);
		
		// check for double method declaration
		if(ast.sym != ((ClassSymbol)arg.get(0)).methods.get(ast.name))
			throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
		
		// check for double parameter declaration
		for(int i = 0; i < ast.sym.parameters.size(); i++)
			for(int j = i + 1; j < ast.sym.parameters.size(); j++)
				if(ast.sym.parameters.get(i).name.equals(ast.sym.parameters.get(j).name))
					throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
		
		ClassSymbol c = (ClassSymbol)arg.get(0);
		
		// check for (invalid) override
		while(c.superClass != null) {
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
					if(!ast.sym.parameters.get(i).type.equals(sm.parameters.get(i).type))
						throw new SemanticFailure(SemanticFailure.Cause.INVALID_OVERRIDE);
				}
			}
			c = c.superClass;
		}
		// check that there is a return statement on every possible control flow path
		if(!hasAReturn(ast.body()) && !ast.sym.returnType.equals(Symbol.PrimitiveTypeSymbol.voidType))
			throw new SemanticFailure(SemanticFailure.Cause.MISSING_RETURN);
		
		return null;
	}
			
	public TypeSymbol varDecl(Ast.VarDecl ast, ArrayList<Symbol> arg) {
		
		ClassSymbol c = (ClassSymbol)arg.get(0);
		MethodSymbol m = (MethodSymbol)arg.get(1);
		dflt(ast,arg);
		
		// check for double declaration within fields of the class
		if(ast.sym.kind.equals(Symbol.VariableSymbol.Kind.FIELD)) {
			if(ast.sym != c.fields.get(ast.name)) 
				throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
			}
		else
			// check for double declaration within local variables
			if(ast.sym != m.locals.get(ast.name))
				throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
				
			if(m != null) {
			// check for double declaration within parameters
				for (VariableSymbol parameter : m.parameters)
					if(ast.name.equals(parameter.name) && ast.sym != parameter)
						throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
			}
		
		return null;
	}

	public TypeSymbol assign(Ast.Assign ast, ArrayList<Symbol> arg) {
		
		ast.right().type = ast.right().accept(this, arg);
		ast.left().type = ast.left().accept(this, arg);
		
		// TODO check that the left-hand side is assignable
		if(!((ast.left() instanceof Ast.Var) || (ast.left() instanceof Ast.Field) || (ast.left() instanceof Ast.Index)))
			throw new SemanticFailure(SemanticFailure.Cause.NOT_ASSIGNABLE);
		
		// check that the type of the right-hand side is a subtype of the type of the left-hand side
		if(!isSubType(ast.right().type,ast.left().type))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Assign types do not match: " + ast.left().type + " = " + ast.right().type);
		
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
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Condition not boolean");
		
		return null;
	}
	
	public TypeSymbol returnStmt(Ast.ReturnStmt ast, ArrayList<Symbol> arg) {
		
		if(ast.arg() == null) {
			if(((MethodSymbol)arg.get(1)).returnType.equals(Symbol.PrimitiveTypeSymbol.voidType))
				return null;
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Empty return");
		}
		
		ast.arg().type = ast.arg().accept(this, arg);
		
		MethodSymbol ms = (MethodSymbol)arg.get(1);
		// check that the argument to the return statement is of a type that is a subtype to the return type of the function
		if(ms.returnType.equals(Symbol.PrimitiveTypeSymbol.voidType) || !(isSubType(ast.arg().accept(this, arg), ms.returnType)))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Return type mismatch " +
				ast.arg().type.name + " of " + ms.returnType + " method: " + ms.name);
		
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
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Condition not boolean");
		
		return null;
	}
	
	public TypeSymbol binaryOp(Ast.BinaryOp ast, ArrayList<Symbol> arg) {
		System.out.println(ast.operator);
		ast.right().type = ast.right().accept(this, arg);
		ast.left().type = ast.left().accept(this, arg);
		
		// check that '==' and '!=' get types such that one is a subtype of the other and return a boolean
		if(ast.operator.equals(BOp.B_EQUAL) || ast.operator.equals(BOp.B_NOT_EQUAL)) {
			if(!(isSubType(ast.right().type,ast.left().type) || isSubType(ast.left().type,ast.right().type)))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "(Not)Equality type mismatch");
			ast.type = Symbol.PrimitiveTypeSymbol.booleanType;
		}
		// check that '||' and '&&' get two booleans and return a boolean
		else if(ast.operator.equals(BOp.B_OR) || ast.operator.equals(BOp.B_AND)) {
			if(!(ast.right().type.equals(Symbol.PrimitiveTypeSymbol.booleanType) && ast.left().type.equals(Symbol.PrimitiveTypeSymbol.booleanType)))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "AND|OR type mismatch");
			ast.type = Symbol.PrimitiveTypeSymbol.booleanType;
		}
		// check that all other binary operators get two integers and return the appropriate type (boolean/integer)
		else {
			if(!(ast.right().type.equals(Symbol.PrimitiveTypeSymbol.intType) && ast.left().type.equals(Symbol.PrimitiveTypeSymbol.intType)))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Binary not 2 ints");
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
		
		ast.type = analyzer.getClassSymbol(ast.typeName);
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that either the cast type is a subtype of the original type or vice versa and return an object of the cast type
		if(!(isSubType(ast.type,ast.arg().type) || isSubType(ast.arg().type,ast.type)))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Wrong cast");
		
		return ast.type;
	}
	
	public TypeSymbol field(Ast.Field ast, ArrayList<Symbol> arg) {
		System.err.println("Field: " + ast.fieldName);
		ast.arg().type = ast.arg().accept(this, arg);
		
		// check that the argument has a class-type
		if(!(ast.arg().type instanceof Symbol.ClassSymbol))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Field parent no class");
		
		ClassSymbol cc = (ClassSymbol)ast.arg().type;
		
		while(cc.superClass != null && !cc.fields.containsKey(ast.fieldName))
			cc = cc.superClass;
		
		// check that the field exists in the class of the calling object or a superclass of it
		if(!cc.fields.containsKey(ast.fieldName))
			throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_FIELD);
		
		ast.sym = cc.getField(ast.fieldName);
		if(ast.sym == null)
			throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_FIELD);
		ast.type = ast.sym.type;
		
		return ast.type;
	}

	public TypeSymbol index(Ast.Index ast, ArrayList<Symbol> arg) {
		
		ast.left().type = ast.left().accept(this, arg);
		ast.right().type = ast.right().accept(this, arg);
		ast.type = analyzer.getClassSymbol(ast.left().type.name);
		
		// check that the index is of type 'int'
		if(!ast.right().type.equals(Symbol.PrimitiveTypeSymbol.intType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Index not int");
		
		// check that the array is of an array type
		if(!(ast.left().type instanceof Symbol.ArrayTypeSymbol))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Class not array");
		
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
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Mothod call not on a object");
		
		ClassSymbol cc = (ClassSymbol)ast.receiver().type;

		while(cc.superClass != null && !cc.methods.containsKey(ast.methodName))
			cc = cc.superClass;
		
		// check that the method exists in the class of the calling object or a superclass of it
		if(!cc.methods.containsKey(ast.methodName))
			throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_METHOD);
		
		ast.sym = cc.getMethod(ast.methodName);
		ast.type = ast.sym.returnType;
		
		// check that there are as many arguments passed as there are formal parameters in the declaration
		if(ast.argumentsWithoutReceiver().size() != ast.sym.parameters.size())
			throw new SemanticFailure(SemanticFailure.Cause.WRONG_NUMBER_OF_ARGUMENTS);
		
		for(int i = 0; i < ast.argumentsWithoutReceiver().size(); i++) {
			ast.argumentsWithoutReceiver().get(i).type = ast.argumentsWithoutReceiver().get(i).accept(this, arg);
		
			// check that each actual argument has the type of the formal one
			if(!isSubType(ast.argumentsWithoutReceiver().get(i).type,ast.sym.parameters.get(i).type))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Parameters in method call mismatch");
		}
		
		return ast.sym.returnType;
	}

	public TypeSymbol newObject(Ast.NewObject ast, ArrayList<Symbol> arg) {
		
		ast.type = analyzer.getClassSymbol(ast.typeName);
		
		return ast.type;
	}

	public TypeSymbol newArray(Ast.NewArray ast, ArrayList<Symbol> arg) {

		ast.type = new Symbol.ArrayTypeSymbol(analyzer.getClassSymbol(ast.typeName));
		
		// check that array size is of integer type
		if(!ast.arg().accept(this, arg).equals(Symbol.PrimitiveTypeSymbol.intType))
			throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Array size not integer");
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
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "! not getting boolean");
			return Symbol.PrimitiveTypeSymbol.booleanType;
		}
		// check that unary '+' and '-' get a integer and return an integer
		else {
			if(!ast.arg().type.equals(Symbol.PrimitiveTypeSymbol.intType))
				throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "+/- not getting int");
			return Symbol.PrimitiveTypeSymbol.intType;
		}
	}

	public TypeSymbol var(Ast.Var ast, ArrayList<Symbol> arg) {
		
		for(int i = 0; i < ((MethodSymbol)arg.get(1)).parameters.size(); i++)
			if(((MethodSymbol)arg.get(1)).parameters.get(i).name.equals(ast.name))
				ast.sym = ((MethodSymbol)arg.get(1)).parameters.get(i);
		
		if(((MethodSymbol)arg.get(1)).locals.containsKey(ast.name))
			ast.sym = ((MethodSymbol)arg.get(1)).locals.get(ast.name);
		
		ClassSymbol actual =(ClassSymbol)arg.get(0);
		while(actual != ClassSymbol.objectType) {
			if(actual.fields.containsKey(ast.name)) {
				ast.sym = actual.fields.get(ast.name);
				break;
			}
			actual = analyzer.getClassSymbol(actual.superClass.name);
		}
		
		// check that the variable exists (was declared)
		if(ast.sym == null)
			throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_VARIABLE, "Var name: " + ast.name);
		
		ast.type = ast.sym.type;
		
		return ast.type;
	}
}