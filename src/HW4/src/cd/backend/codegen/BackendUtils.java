package cd.backend.codegen;

import java.util.Arrays;
import java.util.List;

import cd.Config;
import cd.backend.ExitCode;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast.ClassDecl;
import cd.ir.Symbol.ClassSymbol;

/**
 * Utilities for saving and restoring of registers to the stack. Computing right offsets and asm label names.
 * 
 * @author limo
 *
 */
public class BackendUtils {

	// returns method label
	public static String getMethodLabel(String c_name, String m_name) {
		return "func_" + c_name + "_" + m_name;
	}
	
	// returns vtable label
	public static String getVTableLabel(String c_name) {
		int index = c_name.indexOf("[]");
		if(index != -1)
			c_name = c_name.substring(0, index);
		return "vtable_" + c_name;
	}
	
	// returns vtable label for array
	public static String getVTableArrayLabel(String c_name) {
		int index = c_name.indexOf("[]");
		if(index != -1)
			c_name = c_name.substring(0, index);
		return "v_arr_table_" + c_name;
	}
	
	// pushes given registers onto the stack
	public static void saveRegisters(AssemblyEmitter emit, List<Register> regs) {
		emit.emitComment("Saving registers onto the stack");
		for(Register r : regs) {
			emit.emit("pushl", r);
		}
	}
	
	// restores given registers from the stack
	public static void restoreRegisters(AssemblyEmitter emit, List<Register> regs) {
		emit.emitComment("Restoring registers from the stack");
		// we need to iterate in reverse order because its a stack
		for(int i = regs.size() - 1; i >= 0; i--) {
			emit.emit("popl", regs.get(i));
		}
	}
	
	// pushes and alignes arguments on the stack (in reverse order i.e. they increase to bigger addresses)
	public static void pushArguments(AssemblyEmitter emit, List<String> args, int offset) {
		emit.emitComment("Pushing arguments on the stack");
		int size = allocSpaceSize(offset, args.size());
		emit.emit("subl", AssemblyEmitter.constant(size), Register.ESP);
		for(int i = 0; i < args.size(); i++) {
			emit.emitStore(args.get(i), i * Config.SIZEOF_PTR, Register.ESP);
		}
	}
	
	// returns offset that should be substracted from stack pointer
	public static int allocSpaceSize(int offset, int argsCount) {
		// we need to have 16 bite alignment otherwise we got seg faults  
		// this is not my idea, it was found in assembly offsets tutorial
		int argSpace = argsCount * Config.SIZEOF_PTR;
		return (offset - argSpace) % 16 + 16 + argSpace;
	}
	
	// pops arguments from the stack
	public static void popArguments(AssemblyEmitter emit, List<String> args, int offset) {
		emit.emitComment("Poping arguments");
		int size = allocSpaceSize(offset, args.size());
		emit.emit("addl", AssemblyEmitter.constant(size), Register.ESP);
	}
	
	// returns size of object in memory
	public static int size(ClassDecl decl) {
		return size(decl.sym);
	}
	
	// returns size of object in memory
	public static int size(ClassSymbol decl) {
		return decl.o_table.size();
	}
	
	public static void emitExit(AssemblyEmitter emit, ExitCode eo, int offset) {
		List<String> args = Arrays.asList(AssemblyEmitter.constant(eo.value));
		pushArguments(emit, args, offset);
		emit.emit("call", Config.EXIT);
	}
}
