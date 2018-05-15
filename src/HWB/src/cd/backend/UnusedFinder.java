package cd.backend;

import java.util.*;

import cd.ir.Ast.*;
import cd.ir.Symbol.VariableSymbol;
import cd.util.debug.AstOneLine;
import cd.ir.Ast;
import cd.ir.BasicBlock;

public class UnusedFinder {
	private ExprFinder finder;
	
	public UnusedFinder() {
		finder = new ExprFinder();
	}
	
	/**
	 * Removes assignments to unused variables
	 * @param decl Method to remove local variables from.
	 */
	public void process(MethodDecl decl) {
		List<String> vars = getVariables(decl);
		for(String var : vars) {
			removeFrom(decl.cfg.start, var);
		}
	}
	
	/**
	 * Remove provided variable upwards (to the start)
	 * @param bb to start from
	 * @param var to remove
	 */
	public void removeFrom(BasicBlock bb, String var) {
		for(int i = bb.stmts.size() - 1; i >= 0 ; i--) {
			Set<BasicBlock> visited1 = new HashSet<BasicBlock>();
			Set<BasicBlock> visited2 = new HashSet<BasicBlock>();
			boolean occurs = find(bb, i + 1, var, visited1);
			//System.out.println("Variable " + var + " occures: " + occurs + " after statement " + AstOneLine.toString(bb.stmts.get(i)));
			if(!occurs && bb.stmts.get(i) instanceof Assign && ((Assign)bb.stmts.get(i)).left() instanceof Var
					&& ((Var)((Assign)bb.stmts.get(i)).left()).name.equals(var) &&
					((Var)((Assign)bb.stmts.get(i)).left()).sym.kind == VariableSymbol.Kind.LOCAL &&
					!finder.find(bb.stmts.get(i), "[methodCall]") && !finder.find(bb.stmts.get(i), "read")
					&& !finder.find(bb.stmts.get(i), "(cast)") && !finder.find(bb.stmts.get(i), "[index]")
					&& !finder.find(bb.stmts.get(i), "[new]")) {
				//System.out.println(AstOneLine.toString(bb.stmts.get(i)) + " removed");
				bb.stmts.remove(i);
			}
		}
		
		for(BasicBlock block : bb.predecessors) {
			removeFrom(block, var);
		}
	}
	
	// return all local variables in method
	private List<String> getVariables(MethodDecl ast){
		List<String> vars = new ArrayList<String>();
		for(VarDecl v : ast.decls().childrenOfType(VarDecl.class))
			vars.add(v.name);
		return vars;
	}
	
	// finds usage of variable starting from statement at index in block till the end
	private boolean find(BasicBlock bb, int index, String var, Set<BasicBlock> visited) {
		if(visited.contains(bb))
			return false;
		visited.add(bb);
		
		boolean find = false;
		for(int i = index; i < bb.stmts.size(); i++) {
			find = find || finder.find(bb.stmts.get(i), var);
		}
		
		if(bb.condition != null) {
			find = find || finder.find(bb.condition, var);
		}
		
		if(!find){
			for(BasicBlock block : bb.successors) {
				find = find || find(block, 0, var, visited);
			}
		}
		return find;
	}
}
