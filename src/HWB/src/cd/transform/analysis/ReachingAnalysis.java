package cd.transform.analysis;

import cd.ir.Ast.*;
import cd.ir.BasicBlock;
import cd.ir.ControlFlowGraph;
import cd.ir.Symbol.VariableSymbol.Kind;
import cd.ir.Ast;

import java.util.*;

public class ReachingAnalysis extends DataFlowAnalysis<Set<Definition>>{
	
	public ReachingAnalysis(ControlFlowGraph cfg) {
		super(cfg);
		
		// Figure out gens.
		for (BasicBlock block : cfg.allBlocks) {

			// Get all assignment statements.
			Map<String, Definition> defs = new HashMap<String, Definition>();
			for (Stmt stmt : block.stmts) {
				if (stmt instanceof Assign && ((Assign) stmt).left() instanceof Var) {
					Definition def = new Definition((Assign) stmt);

					// Replaces all previous definitions if they exist.
					defs.put(def.var, def);
				}
			}

			Set<Definition> gen = new HashSet<Definition>();
			gen.addAll(defs.values());
			this.gen.put(block, gen);
		}
		// Figure out kills.

		// TODO Kills from own block? e.g. x = 1; x = 2;

		for (BasicBlock block : cfg.allBlocks) {
			Set<Definition> kill = new HashSet<Definition>();
			for (Definition def : gen.get(block)) {

				// Check if definition with same target in other blocks exist.
				for (BasicBlock otherBlock : cfg.allBlocks) {
					if (otherBlock != block) {
						for (Definition otherDef : gen.get(otherBlock)) {
							if (def.var.equals(otherDef.var)) {
								kill.add(otherDef);
							}
						}
					}
				}
			}
			this.kill.put(block, kill);
		}

		iterate();
		
	}


	private Map<BasicBlock, Set<Definition>> gen = new HashMap<BasicBlock, Set<Definition>>();
	private Map<BasicBlock, Set<Definition>> kill = new HashMap<BasicBlock, Set<Definition>>();


	@Override
	protected Set<Definition> initialState() {
		// TODO Auto-generated method stub
		return new HashSet<Definition>();
	}


	@Override
	protected Set<Definition> startState() {
		// TODO Auto-generated method stub
		return new HashSet<Definition>();
	}


	@Override
	protected Set<Definition> transferFunction(BasicBlock block,
			Set<cd.transform.analysis.Definition> inState) {
		Set<Definition> out = new HashSet<Definition>();
		out.addAll(inState);
		out.removeAll(kill.get(block));
		out.addAll(gen.get(block));
		return out;
	}


	@Override
	protected Set<Definition> join(Set<Set<cd.transform.analysis.Definition>> states) {
		Set<Definition> joined = new HashSet<Definition>();
		for (Set<Definition> state : states) {
			joined.addAll(state);
		}
		return joined;
	}
}
