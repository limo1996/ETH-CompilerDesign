package cd.backend.codegen;
import java.util.HashMap;
import java.util.Map;

import cd.Config;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast.*;

/**
 * Context of each method in visitor. Contains handful information.
 * 
 * @author limo
 *
 */
public class Context {
	private ClassDecl _class;
	private MethodDecl _method;
	
	private Map<String, Integer> offsets;
	
	/**
	 * Indicates whether you should return value or address.
	 */
	public boolean returnValue;
	
	/**
	 * current offset from the stack pointer.
	 */
	public int SP_offset;
	
	/**
	 * Creates new instance of Context class.
	 * @param classDecl
	 */
	public Context(ClassDecl classDecl) {
		this._class = classDecl;
	}
	
	/**
	 * Sets current method to the context
	 * @param methodDecl Method to be set.
	 * @param emit Assembly emitter.
	 */
	public void setMethod(MethodDecl methodDecl, AssemblyEmitter emit) {
		this._method = methodDecl;
		this.returnValue = true;
		this.offsets = new HashMap<String, Integer>();
		
		int offset = 2 * Config.SIZEOF_PTR;
		offsets.put("this", 2 * Config.SIZEOF_PTR);
		// arguments have positive offset, all are of size 4
		for(String arg : methodDecl.argumentNames) {
			offset += Config.SIZEOF_PTR;
			offsets.put(arg, offset);
		}
		// locals have negative offsets
		offset = 0;
		for(String local : methodDecl.sym.locals.keySet()) {
			offset -= Config.SIZEOF_PTR;
			offsets.put(local, offset);
			emit.emitStore(AssemblyEmitter.constant(0), offset, Register.EBP);
		}
		
	}
	
	/**
	 * Gets offset of variable
	 */
	public int getOffset(String var) {
		return this.offsets.get(var);
	}
	
	/**
	 * Gets current class
	 */
	public ClassDecl getCLass() {
		return this._class;
	}
	
	/**
	 * Gets current method
	 */
	public MethodDecl getMethod() {
		return this._method;
	}
	
	/**
	 * Gets current method label
	 */
	public String getMethodLabel() {
		assert(_class != null);
		assert(_method != null);
		
		return BackendUtils.getMethodLabel(this._class.name, this._method.name);
	}
	
	/**
	 * Gets method label
	 * @param m_name name of the method to return label for
	 */
	public String getMethodLabel(String m_name) {
		assert(_class != null);
		
		return BackendUtils.getMethodLabel(this._class.name, m_name);
	}
	
	/**
	 * Gets current vtable
	 */
	public VTable getVTable() {
		assert(_class != null);
		return _class.sym.v_table;
	}
	
	/**
	 * Gets current otable.
	 */
	public OTable getOTable() {
		assert(_class != null);
		return _class.sym.o_table;
	}
}
