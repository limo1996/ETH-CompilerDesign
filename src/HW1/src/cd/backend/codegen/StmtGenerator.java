package cd.backend.codegen;

import cd.Config;
import cd.ToDoException;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast;
import cd.ir.Ast.Var;
import cd.ir.Ast.VarDecl;
import cd.ir.Ast.Assign;
import cd.ir.Ast.BuiltInWrite;
import cd.ir.Ast.BuiltInWriteln;
import cd.ir.Ast.IfElse;
import cd.ir.Ast.MethodCall;
import cd.ir.Ast.MethodDecl;
import cd.ir.Ast.WhileLoop;
import cd.ir.AstVisitor;
import cd.util.debug.AstOneLine;

/**
 * Generates code to process statements and declarations.
 */
class StmtGenerator extends AstVisitor<Register, Void> {
	protected final AstCodeGenerator cg;
	public static final String STR_D = "STR_D";
	public static final String NEW_L = "NEW_L";
	public static final String READ_VAL = "READ_VAL";

	StmtGenerator(AstCodeGenerator astCodeGenerator) {
		cg = astCodeGenerator;
	}

	public void gen(Ast ast) {
		visit(ast, null);
	}

	@Override
	public Register visit(Ast ast, Void arg) {
		try {
			cg.emit.increaseIndent("Emitting " + AstOneLine.toString(ast));
			return super.visit(ast, arg);
		} finally {
			cg.emit.decreaseIndent();
		}
	}

	@Override
	public Register methodCall(MethodCall ast, Void dummy) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register methodDecl(MethodDecl ast, Void arg) {
		// declaration of strings needed for printing and scanning
		cg.emit.emitComment("Declaration of printf and scanf strings");
		cg.emit.emitRaw(Config.DATA_STR_SECTION);
		cg.emit.emitRaw(STR_D + ": " + Config.DOT_STRING + " \"%d\"");
		cg.emit.emitRaw(NEW_L + ": " + Config.DOT_STRING + " \"\\n\"");
		cg.emit.emitRaw(Config.DATA_INT_SECTION);
		// memory location where every read will be loaded
		cg.emit.emitRaw(READ_VAL + ": " + Config.DOT_INT + " 0");
		
		this.visit(ast.decls(), arg);	// generate variables
		
		// Main function declaration and its body(instructions)
		cg.emit.emitComment("Main function declaration and body");
		cg.emit.emitRaw(Config.TEXT_SECTION);
		cg.emit.emitRaw(".global " + Config.MAIN);
		cg.emit.emitRaw(Config.MAIN + ":");
		
		// Asm function prolog
		cg.emit.emitComment("Asm function prolog");
		cg.emit.emit("pushl", Register.EBP);
		cg.emit.emitMove(Register.ESP, Register.EBP);
		cg.emit.emitRaw(".align 16");
		
		this.visit(ast.body(), arg);		// generate code for body of the method
		
		// restore base pointer
		cg.emit.emitComment("Restores base pointer");
		cg.emit.emit("popl", Register.EBP);
		// Return of 0 from main method. Remember result of function is stored in register EAX
		cg.emit.emitComment("Return 0");
    		cg.emit.emitMove(AssemblyEmitter.constant(0), Register.EAX);
    		cg.emit.emitRaw("retl");
		return null;
	}

	@Override
	public Register ifElse(IfElse ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register whileLoop(WhileLoop ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	/**
	 * Generates code for right hand side and than moves result to left hand operand (variable)
	 */
	@Override
	public Register assign(Assign ast, Void arg) {
		// We need to traverse the tree on the right hand side and find out how many reg are needed
		RegCounter rc = new RegCounter();
		rc.visit(ast, arg);
		// Get name of variable on the left
		String name = ((Var)ast.left()).name;
		cg.emit.emitComment("Assignment of " + name + " (" + ast.right().getRegNeeded() + " registers needed)");
		// Generate code for expression on the right and get register where the result is stored.
		Register r = ast.right().accept(cg.eg, arg);
		// Move result from register to memory
		cg.emit.emitMove(r, name);
		// Release register
		cg.rm.releaseRegister(r);
		return null;
	}

	/**
	 * Writes integer passed as parameter in BuildInWrite obj. 
	 * Uses static format string STR_D -> "%d"
	 * Pushes it above stack pointer
	 * Pushes integer to print above STR_D
	 * Calls syscall printf
	 */
	@Override
	public Register builtInWrite(BuiltInWrite ast, Void arg) {
		RegCounter rc = new RegCounter();
		rc.visit(ast.arg(), arg);
		Register r = ast.arg().accept(cg.eg, arg);
		cg.emit.emitComment("--- Write of value in req " + r.repr + "---");
		cg.emit.emitComment(ast.arg().getRegNeeded() + " registers needed");
		cg.emit.emit("subl", AssemblyEmitter.constant(8), Register.ESP);
		cg.emit.emitMove(r, AssemblyEmitter.registerOffset(4, Register.ESP));
		cg.emit.emitMove(AssemblyEmitter.labelAddress(STR_D), AssemblyEmitter.registerOffset(0, Register.ESP));
		cg.emit.emitRaw("calll " + Config.PRINTF);
		cg.emit.emit("addl",  AssemblyEmitter.constant(8), Register.ESP);
		cg.emit.emitComment("--------");
		cg.rm.releaseRegister(r);
		return null;
	}

	/**
	 * Writes new line
	 * Uses static format string NEW_: -> "\n"
	 * Pushes it above stack pointer
	 * Calls syscall printf
	 */
	@Override
	public Register builtInWriteln(BuiltInWriteln ast, Void arg) {
		cg.emit.emitComment("--- Write of new line ---");
		cg.emit.emit("subl", AssemblyEmitter.constant(4), Register.ESP);
		cg.emit.emitMove(AssemblyEmitter.labelAddress(NEW_L), AssemblyEmitter.registerOffset(0, Register.ESP));
		cg.emit.emitRaw("calll " + Config.PRINTF);
		cg.emit.emit("addl",  AssemblyEmitter.constant(4), Register.ESP);
		cg.emit.emitComment("--------");
		return null;
	}
	
	/**
	 * Declares variable in .data section
	 */
	@Override
	public Register varDecl(Ast.VarDecl ast, Void arg) {
		cg.emit.emitComment("Declaration of variable " + ast.name);
		cg.emit.emitRaw(ast.name + ": " + Config.DOT_INT + " 0");
		return null;
	}
}
