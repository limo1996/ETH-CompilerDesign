package cd.backend.codegen;
import java.util.*;

import cd.ir.Ast.Assign;
import cd.ir.Ast.ClassDecl;
import cd.ir.Ast.*;
import cd.ir.Ast.MethodDecl;
import cd.ir.Ast.NullConst;
import cd.ir.Ast.Stmt;
import cd.ir.Ast.Var;
import cd.ir.BasicBlock;
import cd.ir.Symbol.VariableSymbol;
import cd.transform.analysis.Definition;
import cd.transform.analysis.NullRefAnalysis;
import cd.transform.analysis.ReachingAnalysis;

public class CodeContext {

	private Map<String, Integer> arrayToSize;
	private MethodDecl m_decl;
	private ClassDecl c_decl;
	private NullRefAnalysis null_an;
	private BasicBlock _bb;
	private int index;
	
	public CodeContext() {
		arrayToSize = new HashMap<String, Integer>();
	}
	
	public void setClass(ClassDecl c_decl) {
		this.c_decl = c_decl;
	}
	
	public void setMethod(MethodDecl m_delc) {
		this.m_decl = m_delc;
		arrayToSize = new HashMap<String, Integer>();
		null_an = new NullRefAnalysis(m_decl.cfg, m_decl);
	}
	
	public void setBasicBlock(BasicBlock bb) {
		_bb = bb;
	}
	
	public void setStmtIndex(int i) {
		index = i;
	}
	
	public static boolean supported(Expr recv) {
		return recv instanceof ThisRef || (recv instanceof Var && ((Var)recv).sym.kind == VariableSymbol.Kind.LOCAL);
	}
	
	/** 
	 * Finds the definition of variable. 
	 * @return Returns null if there is more than one definition incoming 
	 * into block and block itself contains no definition of it
	 */
	private Expr definition(String var, BasicBlock bb, NullRefAnalysis ra, int i) {
		Expr reach = null;
		List<Expr> ins = new ArrayList<Expr>();
		for(Definition d : ra.inStates.get(bb)){
			if(d.var.equals(var)) {
				ins.add(d.expr);
			}
		}
		//System.out.println(var);
		assert(ins.size() > 0);
		reach = ins.get(0);
		for(Expr e : ins) {
			if(e instanceof NullConst) {
				reach = null;
				break;
			}
		}
		
		for(int it = i - 1; it >= 0; it--) {
			Stmt st = bb.stmts.get(it);
			if(st instanceof Assign && ((Assign)st).left() instanceof Var) {
				String name = ((Var)((Assign)st).left()).name;
				if(name.equals(var)) {
					reach = ((Assign)st).right();
					break;
				}
			}
		}
		
		/*Ast stmt = i < bb.stmts.size() ? bb.stmts.get(i) : bb.condition;
		System.out.println("Variable " + var + " in statement: \n" + 
				AstOneLine.toString(stmt) + "\nis defined as:\n" + 
				AstOneLine.toString(reach));*/

		return reach;
	}
	
	public boolean needsNullCheck(Expr e) {
		if(!supported(e))
			return true;
		if(e instanceof ThisRef)
			return false;
		String name = ((Var)e).name;
		Expr res = definition(name, _bb, null_an, index);
		return res == null || res instanceof NullConst;
	}
	
	public void setArrSize(String name, int size) {
		arrayToSize.put(name, size);
	}
	
	public int getArrSize(String name) {
		if(arrayToSize.containsKey(name))
			return arrayToSize.get(name);
		return 0;
	}
	
	public String left;
}
