package cd.backend;

import cd.ir.BasicBlock;
import cd.transform.analysis.ReachingAnalysis;

public class Context {
	public BasicBlock curr_block;
	public ReachingAnalysis curr_an;
	public int stmt_index;
	
	public Context(ReachingAnalysis ra, BasicBlock bb, int index) {
		curr_block = bb;
		curr_an = ra;
		stmt_index = index;
	}
}
