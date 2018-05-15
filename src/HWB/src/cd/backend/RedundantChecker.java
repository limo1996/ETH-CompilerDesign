package cd.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import cd.ir.AstRewriteVisitor;
import cd.ir.AstVisitor;
import cd.ir.BasicBlock;
import cd.ir.ControlFlowGraph;
import cd.ir.Ast;
import cd.ir.Ast.MethodDecl;
import cd.ir.Ast.*;

/**
 * Goal is to remove redundant expressions within the basic block.
 * i.e.
 * 
 * a = 4 + b1;			// can be removed because it does not matter
 * a = 3 * 9 - b2;
 * write(a);
 * @author limo
 *
 */
public class RedundantChecker extends AstVisitor<Void, Void> {
	
	@Override
	public Void methodDecl(MethodDecl ast, Void arg) {	
		ControlFlowGraph cfg = ast.cfg;
		assert cfg != null;
		
		List<String> variables = this.getVariables(ast);
		// for every basic block in method
		for (BasicBlock blk : cfg.allBlocks) {
			TreeSet<Integer> toRemove = new TreeSet<Integer>();
			ExprFinder finder = new ExprFinder();
			// for every variable in method
			for(String var : variables) {
				// for every statement
				for(int i = 0; i < blk.stmts.size(); i++){
					// if there is a read from variable
					if(finder.find(blk.stmts.get(i), var)) {	
						int curr = i - 1;
						boolean firstWrite = false;
						// go through all previous statements till read on the variable or starting stmt
						while(curr >= 0 && !finder.find(blk.stmts.get(curr), var)) {
							boolean isWrite = isWrite(blk.stmts.get(curr), var);
							// if its write to variable
							if(isWrite) {
								// if its not write of read (we can not skip input)
								if(finder.find(blk.stmts.get(curr), "read") || !finder.find(blk.stmts.get(curr), "methodCall")) {
									firstWrite = true;
								} else if(firstWrite) {
									// if its not closest write we can remove it
									toRemove.add(curr);
								} else {
									// we do not remove first write
									firstWrite = true;
								}
							}
							curr--;
						}
					} 
					if(isWrite(blk.stmts.get(i), var) && !finder.find(blk.stmts.get(i), var)) {
						//System.out.println("Starting " + blk.stmts.get(i) + " find of " + var + ": " + finder.find(blk.stmts.get(i), var));
						int curr = i - 1;
						while(curr >= 0 && !finder.find(blk.stmts.get(curr), var)) {
							boolean isWrite = isWrite(blk.stmts.get(curr), var);
							// if its write to variable
							if(isWrite) {
								// if its not write of read (we can not skip input)
								if(!finder.find(blk.stmts.get(curr), "read") && !finder.find(blk.stmts.get(curr), "methodCall")) {
									//System.out.println("Remove of " + blk.stmts.get(curr));
									toRemove.add(curr);
								}
							}
							curr--;
						}
					}
				}
			}
			for(int i : toRemove.descendingSet()) {
				blk.stmts.remove(i);
			}
		}
		return null;
	}
	
	// return all local variables in method
	private List<String> getVariables(MethodDecl ast){
		List<String> vars = new ArrayList<String>();
		for(VarDecl v : ast.decls().childrenOfType(VarDecl.class))
			vars.add(v.name);
		return vars;
	}
	
	// checks if the stmt is write to var
	private boolean isWrite(Stmt stmt, String var) {
		if(stmt instanceof Assign) {
			Expr a = ((Assign)stmt).left();
			if(a instanceof Var) {
				return ((Var)a).name.equals(var);
			}
			return false;
		}
		return false;
	}
}
