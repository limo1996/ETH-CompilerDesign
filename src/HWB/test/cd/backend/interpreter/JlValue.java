package cd.backend.interpreter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cd.backend.ExitCode;
import cd.ir.Symbol.ArrayTypeSymbol;
import cd.ir.Symbol.ClassSymbol;
import cd.ir.Symbol.PrimitiveTypeSymbol;
import cd.ir.Symbol.TypeSymbol;
import cd.ir.Symbol.VariableSymbol;

// Values:
abstract class JlValue {
	public final TypeSymbol typeSym;
	
	static public JlValue getDefault(TypeSymbol type) {
		JlValue result = null;
		if (type.isReferenceType())
			result = new JlNull();
		else if (type == PrimitiveTypeSymbol.booleanType)
			result = new JlBoolean(false);
		else if (type == PrimitiveTypeSymbol.intType)
			result = new JlInt(0);
		assert result != null;
		return result;
	}

	public JlValue(TypeSymbol s) {
		typeSym = s;
	}

	protected void defaultConversion() {
		throw new StaticError("Type conversion between incompatible types.");
	}
	
	public boolean asBoolean() {
		defaultConversion();
		return false;
	}

	public int asInt() {
		defaultConversion();
		return 0;
	}

	public JlReference asRef() {
		defaultConversion();
		return null;
	}

	public JlObject asObject() {
		defaultConversion();
		return null;
	}

	// Binary Operations
	// Arithmetic
	protected JlValue defaultOperation() {
		throw new StaticError("Operation not supported on " + this.getClass());
	}
	
	public JlValue times(JlValue value) {
		return defaultOperation();
	}

	public JlValue div(JlValue value) {
		return defaultOperation();
	}

	public JlValue add(JlValue value) {
		return defaultOperation();
	}

	public JlValue subtract(JlValue value) {
		return defaultOperation();
	}

	public JlValue mod(JlValue value) {
		return defaultOperation();
	}

	// Boolean
	public JlValue or(JlValue value) {
		return defaultOperation();
	}

	public JlValue and(JlValue value) {
		return defaultOperation();
	}

	// Comparison
	public JlValue less(JlValue value) {
		return defaultOperation();
	}

	public JlValue lessOrEqual(JlValue value) {
		return defaultOperation();
	}

	public JlValue greater(JlValue value) {
		return defaultOperation();
	}

	public JlValue greaterOrEqual(JlValue value) {
		return defaultOperation();
	}

	// Unary Operation
	public JlValue plus() {
		return defaultOperation();
	}

	public JlValue minus() {
		return defaultOperation();
	}

	public JlValue not() {
		return defaultOperation();
	}
}

abstract class JlReference extends JlValue {

	public JlReference(TypeSymbol s) {
		super(s);
	}

	@Override
    public JlReference asRef() {
		return this;
	}

	public abstract JlValue field(VariableSymbol name);

	public abstract void setField(VariableSymbol name, JlValue val);

	public abstract JlValue deref(int index);

	public abstract void setDeref(int index, JlValue val);

	public abstract boolean canBeCastTo(String typeName);

	@Override
    public String toString() {
		return String.format("%s(%x)", typeSym, System.identityHashCode(this));
	}

}

class JlObject extends JlReference {

	protected Map<VariableSymbol, JlValue> fields = new HashMap<VariableSymbol, JlValue>();

	public JlObject(ClassSymbol s) {
		super(s);
	}

	@Override
    public JlObject asObject() {
		return this;
	}

	@Override
	public JlValue field(VariableSymbol name) {
		if (fields.containsKey(name)) {
			return fields.get(name);
		}

		JlValue dflt = JlValue.getDefault(name.type);
		setField(name, dflt);
		return dflt;
	}

	@Override
	public void setField(VariableSymbol name, JlValue val) {
		fields.put(name, val);
	}

	@Override
	public JlValue deref(int index) {
		throw new StaticError("Not an array");
	}

	@Override
	public void setDeref(int index, JlValue val) {
		throw new StaticError("Not an array");
	}

	@Override
	public boolean canBeCastTo(String typeName) {
		// Can always cast to Object:
		if (typeName.equals("Object"))
			return true;

		// Make up a set of acceptable types. Check for circular loops!
		Set<String> superTypes = new HashSet<String>();
		ClassSymbol currentType = (ClassSymbol)typeSym;

		while (!currentType.name.equals("Object")) {
			if (superTypes.contains(currentType.name)) {
				throw new StaticError("Circular inheritance: " + currentType.name);
			}

			superTypes.add(currentType.name);
			currentType = currentType.superClass;
		}

		return superTypes.contains(typeName);
	}
}

class JlArray extends JlReference {

	private JlValue contents[];

	public JlArray(ArrayTypeSymbol s, int size) {
		super(s);

		if (size < 0) {
			throw new DynamicError("Invalid array size: " + size,
					ExitCode.INVALID_ARRAY_SIZE);
		}

		this.contents = new JlValue[size];
		for (int i = 0; i < size; i++) {
			this.contents[i] = JlValue.getDefault(s.elementType);
		}

	}

	@Override
	public JlReference asRef() {
		return this;
	}

	public JlArray asArray() {
		return this;
	}

	@Override
	public JlValue deref(int idx) {

		try {
			return contents[idx];
		} catch (final ArrayIndexOutOfBoundsException ex) {
			throw new DynamicError("Array index out of bounds " + idx,
					ExitCode.INVALID_ARRAY_BOUNDS);
		}

	}

	@Override
	public void setDeref(int idx, JlValue value) {

		try {
			contents[idx] = value;
		} catch (final ArrayIndexOutOfBoundsException ex) {
			throw new DynamicError("Array index out of bounds " + idx,
					ExitCode.INVALID_ARRAY_BOUNDS);
		}

	}

	@Override
	public JlValue field(VariableSymbol name) {
		throw new StaticError("Not an object");
	}

	@Override
	public void setField(VariableSymbol name, JlValue value) {
		throw new StaticError("Not an object");
	}

	@Override
	public boolean canBeCastTo(String typeName) {
		return this.typeSym.name.equals(typeName) || typeName.equals("Object");
	}

}

class JlNull extends JlReference {

	public JlNull() {
		super(ClassSymbol.nullType);
	}
	
	@Override
	public boolean equals(Object o) {

		if (o instanceof JlNull) {
			return true;
		}

		return false;
	}

	@Override
    public JlReference asRef() {
		return this;
	}

	@Override
	public JlObject asObject() {
		throw new DynamicError("Null pointer dereferenced",
				ExitCode.NULL_POINTER);
	}

	@Override
	public JlValue field(VariableSymbol name) {
		throw new DynamicError("Null pointer dereferenced",
				ExitCode.NULL_POINTER);
	}

	@Override
	public void setField(VariableSymbol name, JlValue value) {
		throw new DynamicError("Null pointer dereferenced",
				ExitCode.NULL_POINTER);
	}

	@Override
	public JlValue deref(int idx) {
		throw new DynamicError("Null pointer dereferenced",
				ExitCode.NULL_POINTER);
	}

	@Override
	public void setDeref(int idx, JlValue value) {
		throw new DynamicError("Null pointer dereferenced",
				ExitCode.NULL_POINTER);
	}

	@Override
	public boolean canBeCastTo(String typeName) {
		return true;
	}

	@Override
	public String toString() {
		return "null";
	}

}

class JlInt extends JlValue {

	private final int value;

	JlInt(int value) {
		super(PrimitiveTypeSymbol.intType);
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof JlInt) {
			return value == ((JlInt) o).value;
		}

		return false;
	}

	@Override
	public JlValue times(JlValue obj) {

		if (obj instanceof JlInt) {
			return new JlInt(this.value * ((JlInt) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
	public JlValue div(JlValue obj) {

		if (obj instanceof JlInt) {
			return new JlInt(this.value / ((JlInt) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
	public JlValue mod(JlValue obj) {

		if (obj instanceof JlInt) {
			return new JlInt(this.value % ((JlInt) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
	public JlValue add(JlValue obj) {

		if (obj instanceof JlInt) {
			return new JlInt(this.value + ((JlInt) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
	public JlValue subtract(JlValue obj) {

		if (obj instanceof JlInt) {
			return new JlInt(this.value - ((JlInt) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
    public JlValue less(JlValue obj) {

		if (obj instanceof JlInt) {
			return new JlBoolean(this.value < ((JlInt) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
    public JlValue lessOrEqual(JlValue obj) {

		if (obj instanceof JlInt) {
			return new JlBoolean(this.value <= ((JlInt) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
    public JlValue greater(JlValue obj) {

		if (obj instanceof JlInt) {
			return new JlBoolean(this.value > ((JlInt) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
    public JlValue greaterOrEqual(JlValue obj) {

		if (obj instanceof JlInt) {
			return new JlBoolean(this.value >= ((JlInt) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
	public JlValue plus() {
		return new JlInt(value);
	}

	@Override
	public JlValue minus() {
		return new JlInt(-value);
	}

	@Override
	public int asInt() {
		return value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

}

class JlBoolean extends JlValue {

	private final boolean value;

	JlBoolean(boolean value) {
		super(PrimitiveTypeSymbol.booleanType);
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof JlBoolean) {
			return value == ((JlBoolean) o).value;
		}

		return false;

	}

	@Override
	public JlValue not() {
		return new JlBoolean(!value);
	};

	@Override
	public JlValue or(JlValue obj) {

		if (obj instanceof JlBoolean) {
			return new JlBoolean(this.value || ((JlBoolean) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
	public JlValue and(JlValue obj) {

		if (obj instanceof JlBoolean) {
			return new JlBoolean(this.value && ((JlBoolean) obj).value);
		}

		throw new DynamicError("Invalid type!", ExitCode.INTERNAL_ERROR);

	}

	@Override
	public boolean asBoolean() {
		return value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

}