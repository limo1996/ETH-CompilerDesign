package cd.transform.analysis;

import cd.ir.Ast.*;
import cd.ir.BasicBlock;
import cd.ir.ControlFlowGraph;
import cd.ir.Symbol.PrimitiveTypeSymbol;
import cd.ir.Symbol.VariableSymbol.Kind;
import cd.util.debug.AstOneLine;
import cd.ir.Ast;

import java.util.*;

public class ReachingAnalysis extends DataFlowAnalysis<Set<Definition>>{
	private MethodDecl md;
	private Set<Definition> init_state;
	
	public ReachingAnalysis(ControlFlowGraph cfg, MethodDecl md) {
		super(cfg);
		this.md = md;
		//this.init_state = this.getInitSet();
		
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
		for (BasicBlock block : cfg.allBlocks) {
			Set<Definition> kill = new HashSet<Definition>();
			for (Definition def : gen.get(block)) {

				// Check if definition with same target in other blocks exist.
				for (BasicBlock otherBlock : cfg.allBlocks) {
					if (otherBlock != block) {
						for (Definition otherDef : gen.get(otherBlock)) {
							if (def.var.equals(otherDef.var)) {
								assert otherDef != null;
								kill.add(otherDef);
							}
						}
					}
				}
			}
			
			// Kills from own block
			for(int i = 0; i < block.stmts.size(); i++) {
				Stmt stmt = block.stmts.get(i);
				if (stmt instanceof Assign && ((Assign) stmt).left() instanceof Var) {
					Assign asg = (Assign)stmt;
					String var = ((Var)asg.left()).name;
					int curr = i - 1;
					while(curr >= 0) {
						Stmt stmt2 = block.stmts.get(curr);
						if (stmt2 instanceof Assign && ((Assign) stmt2).left() instanceof Var) {
							Assign asg2 = (Assign) stmt2;
							String var2 = ((Var)asg2.left()).name;
							if(var2.equals(var)) {
								Definition toKill = new Definition(asg2);
								assert toKill != null;
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
	
	private Set<Definition> getInitSet(){
		Set<Definition> defs = new HashSet<Definition>();
		for(VarDecl decl: md.decls().childrenOfType(VarDecl.class)) {
			if(decl.sym.type instanceof PrimitiveTypeSymbol) {
				if(decl.sym.type.equals(PrimitiveTypeSymbol.intType)) {
					defs.add(new Definition(new Assign(new Var(decl.name), new IntConst(0))));
				} else if (decl.sym.type.equals(PrimitiveTypeSymbol.booleanType)) {
					defs.add(new Definition(new Assign(new Var(decl.name), new BooleanConst(false))));
				}
			}
		}
		return defs;
	}
	
	public void print() {
		for (BasicBlock block : cfg.allBlocks) {
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
	
	private Definition getDef(Assign asg, Set<Definition> defs) {
		for(Definition def : defs) {
			System.out.println(AstOneLine.toString(def.assign) + " == " + AstOneLine.toString(asg) + " ==> " + def.assign.equals(asg));
			if(def.assign.equals(asg))
				return def;
		}
		return null;
	}


	public Map<BasicBlock, Set<Definition>> gen = new HashMap<BasicBlock, Set<Definition>>();
	public Map<BasicBlock, Set<Definition>> kill = new HashMap<BasicBlock, Set<Definition>>();


	@Override
	protected Set<Definition> initialState() {
		return new HashSet<Definition>();
		//return this.init_state;
	}


	@Override
	protected Set<Definition> startState() {
		return new HashSet<Definition>();
		//return this.init_state;
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
