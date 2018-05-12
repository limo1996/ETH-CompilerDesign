package cd.transform.analysis;

import cd.ir.Ast.Assign;
import cd.ir.Ast.Var;
import cd.ir.Symbol.VariableSymbol.Kind;

public class Definition {
	public String var;
	public Definition(Assign assign) {
		// support only locals
		assert assign.left() instanceof Var && ((Var)assign.left()).sym.kind != Kind.FIELD;
		this.var = ((Var)assign.left()).name;
	}
}
