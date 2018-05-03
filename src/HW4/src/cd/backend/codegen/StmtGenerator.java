package cd.backend.codegen;

import static cd.backend.codegen.AssemblyEmitter.constant;
import static cd.backend.codegen.RegisterManager.STACK_REG;

import java.util.Arrays;
import java.util.List;

import cd.Config;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast;
import cd.ir.Ast.Assign;
import cd.ir.Ast.BuiltInWrite;
import cd.ir.Ast.BuiltInWriteln;
import cd.ir.Ast.ClassDecl;
import cd.ir.Ast.Expr;
import cd.ir.Ast.IfElse;
import cd.ir.Ast.MethodCall;
import cd.ir.Ast.MethodDecl;
import cd.ir.Ast.ReturnStmt;
import cd.ir.Ast.WhileLoop;
import cd.ir.AstVisitor;
import cd.ir.Symbol.MethodSymbol;
import cd.ir.Symbol.PrimitiveTypeSymbol;
import cd.util.debug.AstOneLine;

/**
 * Generates code to process statements and declarations.
 */
class StmtGenerator extends AstVisitor<Register, Context> {
	protected final AstCodeGenerator cg;

	StmtGenerator(AstCodeGenerator astCodeGenerator) {
		cg = astCodeGenerator;
	}

	public void gen(Ast ast) {
		visit(ast, null);
	}

	@Override
	public Register visit(Ast ast, Context arg) {
		try {
			cg.emit.increaseIndent("Emitting " + AstOneLine.toString(ast));
			return super.visit(ast, arg);
		} finally {
			cg.emit.decreaseIndent();
		}
	}

	@Override
	public Register methodCall(MethodCall ast, Context arg) {
		Register ret_value = cg.eg.visit(ast.getMethodCallExpr(), arg);
		if(!ast.getMethodCallExpr().type.equals(PrimitiveTypeSymbol.voidType)) {
			assert ret_value != null;
			 	// return value not used
		}
		if(ret_value != null)
			cg.rm.releaseRegister(ret_value);
		return null;
	}

	public Register methodCall(MethodSymbol sym, List<Expr> allArguments) {
		throw new RuntimeException("Not required");
	}

	// Emit vtable for arrays of this class:
	@Override
	public Register classDecl(ClassDecl ast, Context arg) {
		// classes are not really relevant
		// they are just functions with some context
		return visitChildren(ast, arg);
	}

	/**
	 * Emits method code.
	 */
	@Override
	public Register methodDecl(MethodDecl ast, Context arg) {
		// each method starts with label
		cg.emit.emitLabel(arg.getMethodLabel(ast.name));
		
		// enter method with size of local parameters.
		int localsSize = ast.sym.locals.size() * Config.SIZEOF_PTR;
		cg.emit.emit("enter", AssemblyEmitter.constant(localsSize), AssemblyEmitter.constant(0));
		
		// set method to context and emit locals on the stack.
		arg.setMethod(ast, cg.emit);
		
		// save registers that should be saved by us.
		BackendUtils.saveRegisters(cg.emit, Arrays.asList(RegisterManager.CALLEE_SAVE));
		
		// current offset is 8 for enter + return and length of saved registers.
		arg.SP_offset = -1 * (localsSize + 8 + (RegisterManager.CALLEE_SAVE.length * Config.SIZEOF_PTR));
		visit(ast.body(), arg);
		
		// restore registers and add to offset.
		BackendUtils.restoreRegisters(cg.emit, Arrays.asList(RegisterManager.CALLEE_SAVE));
		arg.SP_offset += RegisterManager.CALLEE_SAVE.length * Config.SIZEOF_PTR;
		
		// emit method suffix.
		cg.emitMethodSuffix(ast.returnType.equals("void"));
		return null;
	}

	@Override
	public Register ifElse(IfElse ast, Context arg) {
	
		/*
		 * Generate code for the evaluation of the expression
		 * and a label to jump to if it evaluates to false.
		 */
		Register condition = cg.eg.visit(ast.condition(), arg);
		String afterThen = cg.emit.uniqueLabel();
		String afterElse = cg.emit.uniqueLabel();
		
		/*
		 * Test whether the condition register holds 0 (false)
		 * and jump if so.
		 */
		cg.emit.emit("cmp", AssemblyEmitter.constant(0), condition);
		cg.emit.emit("je", afterThen);
		cg.rm.releaseRegister(condition);
		
		/*
		 * Generate code for the then-body.
		 */
		visit(ast.then(), arg);
		
        // skip around else-part
        cg.emit.emit("jmp", afterElse);
        cg.emit.emitLabel(afterThen);

        // code for else
        visit(ast.otherwise(), arg);

        cg.emit.emitLabel(afterElse);
		return null;
	}

	@Override
	public Register whileLoop(WhileLoop ast, Context arg) {
		String labelBefore = cg.emit.uniqueLabel();
        cg.emit.emitLabel(labelBefore);

        // code for condition
        Register cond = cg.eg.visit(ast.condition(), arg);

        // loop check
        cg.emit.emit("cmp", constant(0), cond);
        cg.rm.releaseRegister(cond);
        
        // end loop
        String labelAfter = cg.emit.uniqueLabel();
        cg.emit.emit("je", labelAfter);

        // generate code for loop-body
        visit(ast.body(), arg);

        // loop again
        cg.emit.emit("jmp", labelBefore);
        cg.emit.emitLabel(labelAfter);
		return null;

		/* I've tried different approach to it cause it is maybe the reason for server TIMEOUT
		// Generate labels needed.
		String conditionLabel = cg.emit.uniqueLabel();
		String bodyLabel = cg.emit.uniqueLabel();
		
		// Unconditionally jump into the expression evaluation from outside.
		cg.emit.emit("jmp", conditionLabel);
		
		// Emit label and code for the body.
		cg.emit.emitLabel(bodyLabel);
		visit(ast.body(), arg);
		
		// Jump here when entering the loop for the first time.
		cg.emit.emitLabel(conditionLabel);
		
		// Generate code for the evaluation of the condition.
		Register condition = cg.eg.visit(ast.condition(), arg);
		
		// Test whether the condition is non-zero (true)
		// and jump to the body if so.
		cg.emit.emit("cmpl", AssemblyEmitter.constant(0), condition);
		cg.emit.emit("jne", bodyLabel);
		cg.rm.releaseRegister(condition);
		return null;*/
	}

	@Override
	public Register assign(Assign ast, Context arg) {
		// left hand side requires to be pointer to memory.
		arg.returnValue = false;
		Register left = cg.eg.visit(ast.left(), arg);
		// right hand side has to be a value.
		arg.returnValue = true;
		Register right = cg.eg.visit(ast.right(), arg);
		// store to the left register and release both of them.
		cg.emit.emitStore(right, 0, left);
		cg.rm.releaseRegister(left);
		cg.rm.releaseRegister(right);
		return null;
	}

	/**
	 * Emits write of integer to the console.
	 */
	@Override
	public Register builtInWrite(BuiltInWrite ast, Context arg) {
		// push arguments needed on the stack, call printf and pop them
		Register reg = cg.eg.visit(ast.arg(), arg);
		List<String> arguments = Arrays.asList("$STR_D", reg.repr);
		BackendUtils.pushArguments(cg.emit, arguments, arg.SP_offset);
		cg.emit.emit("call", Config.PRINTF);
		BackendUtils.popArguments(cg.emit, arguments, arg.SP_offset);
		cg.rm.releaseRegister(reg);
		return null;
	}

	/**
	 * Emits write line statement.
	 */
	@Override
	public Register builtInWriteln(BuiltInWriteln ast, Context arg) {
		// push arguments needed on the stack, call printf and pop them
		List<String> arguments = Arrays.asList("$STR_NL");
		BackendUtils.pushArguments(cg.emit, arguments, arg.SP_offset);
		cg.emit.emit("call", Config.PRINTF);
		BackendUtils.popArguments(cg.emit, arguments, arg.SP_offset);
		return null;
	}

	/**
	 * Emits return from the method.
	 */
	@Override
	public Register returnStmt(ReturnStmt ast, Context arg) {
		// if there is an expression in if process it first
		if(ast.arg() != null) {
			Register ret = cg.eg.visit(ast.arg(), arg);
			cg.emit.emitMove(ret, Register.EAX);
			cg.rm.releaseRegister(ret);
		}
		// emit ret and leave in asm
		//cg.emitMethodSuffix(ast.arg() == null);
		return null;	
	}
}
