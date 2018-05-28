package cd.transform.analysis;

import cd.ir.Ast.Assign;
import cd.ir.Ast.Expr;
import cd.ir.Ast.Field;
import cd.ir.Symbol.VariableSymbol.Kind;
import cd.util.debug.AstOneLine;

public class Definition2 {
	public String var;
	public Expr expr;
	public Assign assign;
	public Definition2(Assign assign) {
		// support only locals
		assert assign.left() instanceof Field;
		this.var = ((Field)assign.left()).fieldName;
		this.expr = assign.right();
		this.assign = assign;
	}
	
	@Override
	public String toString() {
		return AstOneLine.toString(assign);
	}
}
