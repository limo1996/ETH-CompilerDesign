package cd.backend.codegen;

import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast.BinaryOp;
import cd.ir.Ast.BooleanConst;
import cd.ir.Ast.BuiltInRead;
import cd.ir.Ast.Cast;
import cd.ir.Ast.Expr;
import cd.ir.Ast.Field;
import cd.ir.Ast.Index;
import cd.ir.Ast.IntConst;
import cd.ir.Ast.NewArray;
import cd.ir.Ast.NewObject;
import cd.ir.Ast.NullConst;
import cd.ir.Ast.ThisRef;
import cd.ir.Ast.UnaryOp;
import cd.ir.Ast.Var;
import cd.ir.AstVisitor;

/**
 * Traverses the expression tree and assigns to each node how many registers are needed for code generation.
 */
public class RegCounter extends AstVisitor<Register, Void> {

	public Register gen(Expr ast) {
		return visit(ast, null);
	}

	@Override
	public Register visit(Expr ast, Void arg) {
		try {
			return super.visit(ast, null);
		} finally {
		}
	}

	/**
	 * Binary operator needs max of registers of children. If both children need
	 * same amount of registers than operator needs +1 registers.
	 */
	@Override
	public Register binaryOp(BinaryOp ast, Void arg) {
		assert(ast.children().size() == 2);
		super.visitChildren(ast, arg);
		int leftChild = ast.children().get(0).getRegNeeded();
		int rightChild = ast.children().get(1).getRegNeeded();
		if(leftChild == rightChild)
			ast.setRegNeeded(leftChild + 1);
		else 
			ast.setRegNeeded(Math.max(leftChild, rightChild));
		System.out.println("Binary - " + ast.getRegNeeded());
		return null;
	}

	/**
	 * Boolean constant needs one register.
	 */
	@Override
	public Register booleanConst(BooleanConst ast, Void arg) {
		ast.setRegNeeded(1);
		return null;
	}

	/**
	 * Read needs one register for the result.
	 */
	@Override
	public Register builtInRead(BuiltInRead ast, Void arg) {
		ast.setRegNeeded(1);
		return null;
	}

	@Override
	public Register cast(Cast ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register index(Index ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	/**
	 * Integer constant needs one register.
	 */
	@Override
	public Register intConst(IntConst ast, Void arg) {
		System.out.println("int const - 1");
		ast.setRegNeeded(1);
		return null;
	}

	@Override
	public Register field(Field ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register newArray(NewArray ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register newObject(NewObject ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register nullConst(NullConst ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register thisRef(ThisRef ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	/**
	 * Unary operator needs one register.
	 */
	@Override
	public Register unaryOp(UnaryOp ast, Void arg) {
		System.out.println("unary - 1");
		ast.setRegNeeded(1);
		return null;
	}
	
	/**
	 * Variable needs one register.
	 */
	@Override
	public Register var(Var ast, Void arg) {
		System.out.println("var - 1");
		ast.setRegNeeded(1);
		return null;
	}
}
