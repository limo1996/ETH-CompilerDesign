package cd.transform;

import cd.ir.Ast;
import cd.ir.Ast.IfElse;
import cd.ir.Ast.MethodDecl;
import cd.ir.Ast.Seq;
import cd.ir.Ast.Stmt;
import cd.ir.Ast.WhileLoop;
import cd.ir.AstVisitor;
import cd.ir.BasicBlock;
import cd.ir.ControlFlowGraph;

public class CfgBuilder {
	
	ControlFlowGraph cfg;
	
	public void build(MethodDecl mdecl) {
		cfg = mdecl.cfg = new ControlFlowGraph();
		cfg.start = cfg.newBlock(); // Note: Use newBlock() to create new basic blocks
		cfg.end = cfg.newBlock(); // unique exit block to which all blocks that end with a return stmt. lead
		
		{
			BasicBlock lastInBody = new Visitor().visit(mdecl.body(), cfg.start);
			if (lastInBody != null) cfg.connect(lastInBody, cfg.end);
		}
		
		// CFG and AST are not synchronized, only use CFG from now on
		mdecl.setBody(null);
	}
	
	protected class Visitor extends AstVisitor<BasicBlock, BasicBlock> {
		
		@Override
		protected BasicBlock dfltStmt(Stmt ast, BasicBlock arg) {
			if (arg == null) return null; // dead code, no need to generate anything
			arg.stmts.add(ast);
			return arg;
		}
		
		@Override
		public BasicBlock ifElse(IfElse ast, BasicBlock arg) {
			if (arg == null) return null; // dead code, no need to generate anything
			cfg.terminateInCondition(arg, ast.condition());			
			BasicBlock then = visit(ast.then(), arg.trueSuccessor());
			BasicBlock otherwise = visit(ast.otherwise(), arg.falseSuccessor());
			if (then != null && otherwise != null) { 
				return cfg.join(then, otherwise);
			} else if (then != null) {
				BasicBlock newBlock = cfg.newBlock();
				cfg.connect(then, newBlock);
				return newBlock;
			} else if (otherwise != null) {
				BasicBlock newBlock = cfg.newBlock();
				cfg.connect(otherwise, newBlock);
				return newBlock;
			} else {
				return null;
			}
		}

		@Override
		public BasicBlock seq(Seq ast, BasicBlock arg_) {
			BasicBlock arg = arg_;
			for (Ast child : ast.children())
				arg = this.visit(child, arg);
			return arg;
		}

		@Override
		public BasicBlock whileLoop(WhileLoop ast, BasicBlock arg) {
			if (arg == null) return null; // dead code, no need to generate anything
			BasicBlock cond = cfg.join(arg);
			cfg.terminateInCondition(cond, ast.condition());
			BasicBlock body = visit(ast.body(), cond.trueSuccessor());
			if (body != null) cfg.connect(body, cond);
			return cond.falseSuccessor();		
		}
		
		@Override
		public BasicBlock returnStmt(Ast.ReturnStmt ast, BasicBlock arg) {
			if (arg == null) return null; // dead code, no need to generate anything
			arg.stmts.add(ast);
			cfg.connect(arg, cfg.end);
			return null; // null means that this block leads nowhere else 
		}
		
	}

}
