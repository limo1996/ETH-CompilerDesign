package cd.backend.interpreter;

/** Thrown in cases that should be ruled out by static analysis: */
@SuppressWarnings("serial")
public
class StaticError extends RuntimeException {
	public StaticError(String message) {
		super(message);
	}		
}