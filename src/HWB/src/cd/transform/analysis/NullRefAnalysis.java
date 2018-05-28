package cd.transform.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cd.ir.BasicBlock;
import cd.ir.ControlFlowGraph;
import cd.ir.Ast.*;
import cd.ir.Symbol.ClassSymbol;
import cd.ir.Symbol.PrimitiveTypeSymbol;
import cd.ir.Symbol.TypeSymbol;
import cd.ir.Symbol.VariableSymbol;

public class NullRefAnalysis extends DataFlowAnalysis<Set<Definition>> {
	private MethodDecl md;
	
	/*
	 * TODO: - parameters init state => x=x
	 *  	 - other variables x = null
	 */
	public NullRefAnalysis(ControlFlowGraph cfg, MethodDecl md) {
		super(cfg);
		this.md = md;
		Map<Assign, Definition> as_def = new HashMap<Assign, Definition>();
		// Figure out gens.
		for (BasicBlock block : cfg.allBlocks) {
			// Get all assignment statements.
			Map<String, Definition> defs = new HashMap<String, Definition>();
			for (Stmt stmt : block.stmts) {
				if (stmt instanceof Assign && ((Assign) stmt).left() instanceof Var) {
					Definition def = new Definition((Assign) stmt);
					as_def.put((Assign)stmt, def);
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
								kill.add(otherDef);
							}
						}
					}
				}
				
				for (Definition init_def : this.getInitSet()) {
					if (def.var.equals(init_def.var)) {
						kill.add(init_def);
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
								Definition toKill = as_def.get(asg2);
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
			if(decl.sym.type instanceof ClassSymbol) {
				defs.add(new Definition(new Assign(getLocalVar(decl.name, decl.sym.type), new NullConst())));
			}
		}
		
		/*for(int i = 0; i < md.argumentNames.size(); i++) {
			if(md.argumentTypes.get(i).equals("int")) {
				Var param = getParamVar(md.argumentNames.get(i), false);
				defs.add(new Definition(new Assign(param, param)));
			} else if(md.argumentTypes.get(i).equals("boolean")) {
				Var param = getParamVar(md.argumentNames.get(i), true);
				defs.add(new Definition(new Assign(param, param)));
			}
		}*/
		return defs;
	}
	
	private Var getLocalVar(String name, TypeSymbol sym) {
		return Var.withSym(new VariableSymbol(name, (ClassSymbol)sym, VariableSymbol.Kind.LOCAL));
	}
	
	private Var getParamVar(String name, TypeSymbol sym) {
		return Var.withSym(new VariableSymbol(name, (ClassSymbol)sym, VariableSymbol.Kind.PARAM));
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


	public Map<BasicBlock, Set<Definition>> gen = new HashMap<BasicBlock, Set<Definition>>();
	public Map<BasicBlock, Set<Definition>> kill = new HashMap<BasicBlock, Set<Definition>>();


	@Override
	protected Set<Definition> initialState() {
		return new HashSet<Definition>();
	}


	@Override
	protected Set<Definition> startState() {
		return getInitSet();
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
