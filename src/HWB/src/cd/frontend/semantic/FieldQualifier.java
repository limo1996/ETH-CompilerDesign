package cd.frontend.semantic;

import cd.ir.Ast;
import cd.ir.Ast.ClassDecl;
import cd.ir.Ast.Var;
import cd.ir.AstRewriteVisitor;
import cd.ir.Symbol.ClassSymbol;

/**
 * Runs after the semantic check and rewrites expressions to be more normalized.
 * References to a field {@code foo} are rewritten to always use
 * {@link Ast.Field} objects (i.e., {@code this.foo}.
 */
public class FieldQualifier {

	public void rewrite(ClassDecl cd) {
		AstRewriteVisitor<ClassSymbol> rewriter = new AstRewriteVisitor<ClassSymbol>() {
			@Override
			public Ast var(Var ast, ClassSymbol cs) {
				switch (ast.sym.kind) {
				case PARAM:
				case LOCAL:
					// Leave params or local variables alone
					return ast;
				case FIELD:
					// Convert an implicit field reference to "this.foo"
					Ast.Field f = new Ast.Field(new Ast.ThisRef(), ast.name);
					f.arg().type = cs;
					f.sym = ast.sym;
					f.type = ast.type;
					return f;
				}
				throw new RuntimeException("Unknown kind of var");
			}
		};

		cd.accept(rewriter, cd.sym);
	}

}