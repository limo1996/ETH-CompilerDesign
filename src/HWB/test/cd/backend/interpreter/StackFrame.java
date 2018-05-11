package cd.backend.interpreter;

import java.util.HashMap;
import java.util.Map;

import cd.ir.Symbol.VariableSymbol;

// The stack:
class StackFrame {
	
	private final JlObject thisPointer;
	private final Map<VariableSymbol, JlValue> variables;	
	
	public StackFrame(JlObject thisPointer) {
		this.thisPointer = thisPointer;
		this.variables = new HashMap<VariableSymbol, JlValue>();	
	}
	
	public JlValue var(VariableSymbol name) {
		if (variables.containsKey(name)) {
			return variables.get(name);
		}
		
		JlValue dflt = JlValue.getDefault(name.type);
		setVar(name, dflt);
		return dflt;
	}

	public void setVar(VariableSymbol name, JlValue val) {
		variables.put(name, val);
	}
	
	public String toString() {
		return String.format("StackFrame(%s) {%s}", System.identityHashCode(this), variables.toString());
	}
	
	public JlObject getThisPointer() {
		return thisPointer;
	}
	
}