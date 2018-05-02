package cd.backend.codegen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cd.Config;
import cd.ir.Symbol.ClassSymbol;

/**
 * Table in data section that takes care of calling the right method. 
 * Can be emitted to asm but performs the same functions in java as well.
 * 
 * @author limo
 *
 */
public class VTable {
	// data class for storing method offset pair
	private class MethodOffset {
		public String method;
		public int offset;
		public MethodOffset(String method, int offset) {
			this.method = method;
			this.offset = offset; 
		}
	}
	
	private String name;
	
	// we need to keep key as method name so we override methods from super classes
	private Map<String, MethodOffset> methods = new HashMap<String, MethodOffset>();
	
	// creates new instance of VTable
	public VTable(ClassSymbol sym) {
		this.name = sym.name;
		if(sym.superClass != null) {
			methods.put("parent", new MethodOffset(sym.superClass.v_table.name(), 0));
			methods.putAll(sym.superClass.v_table.methods);
		} else {
			methods.put("parent", new MethodOffset("0", 0));
		}
		for(String key : sym.methods.keySet()) {
			if(methods.containsKey(key)) {
				MethodOffset mo = methods.get(key);
				mo.method = label(key);
			} else {
				methods.put(key, new MethodOffset(label(key), methods.size()));
			}
		}
	}
	
	// returns asm label of vtable's method
	public String label(String method) {
		return BackendUtils.getMethodLabel(name, method);
	}
	
	// emits itself into emitter
	public void emit(AssemblyEmitter emit) {
		emit.emitComment("VTable for " + this.name);
		emit.emitLabel(BackendUtils.getVTableLabel(name));
		String[] finalLabels = new String[methods.size()];
		for(MethodOffset mo : methods.values()) {
			finalLabels[mo.offset] = mo.method;
		}
		for(String label : finalLabels) {
			emit.emitConstantData(label);
		}
	}
	
	// emits array of this type
	public void emitArray(AssemblyEmitter emit) {
		emit.emitComment("VTable for " + this.name + " array");
		emit.emitLabel(BackendUtils.getVTableArrayLabel(name));
		emit.emitConstantData(BackendUtils.getVTableLabel("Object"));
	}
		
	// name
	public String name() {
		return BackendUtils.getVTableLabel(name);
	}
	
	// returns methos offset
	public int methodOffset(String name) {
		return methods.get(name).offset * Config.SIZEOF_PTR;
	}
}
