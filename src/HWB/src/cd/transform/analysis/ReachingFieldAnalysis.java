package cd.transform.analysis;

import cd.ir.Ast.*;
import cd.ir.BasicBlock;
import cd.ir.ControlFlowGraph;
import cd.ir.Symbol.PrimitiveTypeSymbol;
import cd.ir.Symbol.VariableSymbol;
import cd.ir.Symbol.VariableSymbol.Kind;
import cd.util.debug.AstOneLine;
import cd.ir.Ast;

import java.util.*;

public class ReachingFieldAnalysis extends DataFlowAnalysis<Set<Definition2>>{
	private MethodDecl md;
	
	/*
	 * TODO: - parameters init state => x=x
	 *  	 - other variables x = null
	 */
	public ReachingFieldAnalysis(ControlFlowGraph cfg, MethodDecl md) {
		super(cfg);
		this.md = md;
		Map<Assign, Definition2> as_def = new HashMap<Assign, Definition2>();
		// Figure out gens.
		for (BasicBlock block : cfg.allBlocks) {
			
			// Get all assignment statements.
			Map<String, Definition2> defs = new HashMap<String, Definition2>();
			for (Stmt stmt : block.stmts) {
				if (stmt instanceof Assign && ((Assign) stmt).left() instanceof Field &&
						((Field)((Assign) stmt).left()).arg() instanceof ThisRef) {
					Definition2 def = new Definition2((Assign) stmt);
					as_def.put((Assign)stmt, def);
					// Replaces all previous definitions if they exist.
					defs.put(def.var, def);
				}
			}

			Set<Definition2> gen = new HashSet<Definition2>();
			gen.addAll(defs.values());
			this.gen.put(block, gen);
		}
		// Figure out kills.
		for (BasicBlock block : cfg.allBlocks) {
			Set<Definition2> kill = new HashSet<Definition2>();
			for (Definition2 def : gen.get(block)) {

				// Check if definition with same target in other blocks exist.
				for (BasicBlock otherBlock : cfg.allBlocks) {
					if (otherBlock != block) {
						for (Definition2 otherDef : gen.get(otherBlock)) {
							if (def.var.equals(otherDef.var)) {
								kill.add(otherDef);
							}
						}
					}
				}
			}
			
			// Kills from own block
			for(int i = 0; i < block.stmts.size(); i++) {
				Stmt stmt = block.stmts.get(i);
				if (stmt instanceof Assign && ((Assign) stmt).left() instanceof  Field &&
						((Field)((Assign) stmt).left()).arg() instanceof ThisRef) {
					Assign asg = (Assign)stmt;
					String var = ((Field)asg.left()).fieldName;
					int curr = i - 1;
					while(curr >= 0) {
						Stmt stmt2 = block.stmts.get(curr);
						if (stmt2 instanceof Assign && ((Assign) stmt2).left() instanceof Var) {
							Assign asg2 = (Assign) stmt2;
							String var2 = ((Var)asg2.left()).name;
							if(var2.equals(var)) {
								Definition2 toKill = as_def.get(asg2);
								kill.add(toKill);
								break;
							}
						}
						curr--;
					}
				}
			}
			
			this.kill.put(block, kill);
		}

		iterate();
		
	}
	
	public void print() {
		for (BasicBlock block : cfg.allBlocks) {
			if(block == cfg.end)
				System.out.println("=============\nEND\n==========");
			System.out.println("Block: " + block.index);
			System.out.println("Gen:");
			System.out.println(gen.get(block));
			System.out.println("Kill:");
			System.out.println(kill.get(block));
			System.out.println("InStates:");
			System.out.println(inStates.get(block));
			System.out.println("OutStates:");
			System.out.println(outStates.get(block));
			System.out.println("--------------");
		}
	}

	public Set<Definition2> getOut(){
		return outStates.get(cfg.end);
	}
	public Map<BasicBlock, Set<Definition2>> gen = new HashMap<BasicBlock, Set<Definition2>>();
	public Map<BasicBlock, Set<Definition2>> kill = new HashMap<BasicBlock, Set<Definition2>>();


	@Override
	protected Set<Definition2> initialState() {
		return new HashSet<Definition2>();
	}


	@Override
	protected Set<Definition2> startState() {
		return new HashSet<Definition2>();
	}


	@Override
	protected Set<Definition2> transferFunction(BasicBlock block,
			Set<cd.transform.analysis.Definition2> inState) {
		Set<Definition2> out = new HashSet<Definition2>();
		out.addAll(inState);
		out.removeAll(kill.get(block));
		out.addAll(gen.get(block));
		return out;
	}


	@Override
	protected Set<Definition2> join(Set<Set<cd.transform.analysis.Definition2>> states) {
		Set<Definition2> joined = new HashSet<Definition2>();
		for (Set<Definition2> state : states) {
			joined.addAll(state);
		}
		return joined;
	}
}
