package cd.backend.interpreter;

import cd.backend.ExitCode;
import cd.util.FileUtil;

/** Thrown in cases that are not ruled out by static analysis: */
@SuppressWarnings("serial")
public class DynamicError extends RuntimeException {
    private ExitCode code;
	
	public DynamicError(String message, ExitCode code) {
		super(message);
		this.code = code;
	}
	
	public DynamicError(Throwable thr, ExitCode code) {
		super(thr);
		this.code = code;
	}
	
	public ExitCode getExitCode() {
		return this.code;
	}
	/** 
	 * Returns a string exactly like the one that 
	 * {@link FileUtil#runCommand(java.io.File, String[], String[], String, boolean)}
	 * returns when a command results in an error. */
	public String format() {
		return "Error: " + code.value + "\n";
	}

}