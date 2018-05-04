package cd.backend.codegen;

import java.util.ArrayList;
import java.util.List;

import cd.Config;
import cd.ir.Symbol.ClassSymbol;

/**
 * Class that manages offsets for a class. Copies fields of super class.
 * @author limo
 *
 */
public class OTable {
	private List<String> offsets;
	private ClassSymbol decl;
	public OTable(ClassSymbol decl) {
		this.decl = decl;
		this.offsets = new ArrayList<String>();
		if(decl.equals(ClassSymbol.objectType))
			offsets.add(BackendUtils.getVTableLabel(decl.name));
		else {
			offsets.addAll(decl.superClass.o_table.offsets);
			offsets.set(0, BackendUtils.getVTableLabel(decl.name));
			for(String key : decl.fields.keySet()) {
				offsets.add(decl.name + "." + key);
			}
		}
	}
	
	public void debug() {
		System.out.println("OTable for " + decl.name + ": ");
		int i = 0;
		for(String offset : offsets) {
			System.out.println(offset + ": " + i);
			i++;
		}
	}
	
	/**
	 * Returns offset of the field in memory.
	 * @param field of the object
	 * @return offsets of the field.
	 */
	public int offsetOf(String field) {
		//System.out.println("Offset of: " + field);
		int index = offsets.indexOf(field);
		return index * Config.SIZEOF_PTR;
	}
	
	/**
	 * Gets object size.
	 * @return size of the object
	 */
	public int size() {
		return offsets.size();
	}
}
