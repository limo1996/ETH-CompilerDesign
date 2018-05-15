package cd.transform.analysis;

import cd.ir.Ast.Assign;
import cd.ir.Ast.Expr;
import cd.ir.Ast.Var;
import cd.ir.Symbol.VariableSymbol.Kind;
import cd.util.debug.AstOneLine;

public class Definition {
	public String var;
	public Expr expr;
	public Assign assign;
	public Definition(Assign assign) {
		// support only locals
		assert assign.left() instanceof Var && ((Var)assign.left()).sym.kind != Kind.FIELD;
		this.var = ((Var)assign.left()).name;
		this.expr = assign.right();
		this.assign = assign;
	}
	
	@Override
	public String toString() {
		return AstOneLine.toString(assign);
	}
}
