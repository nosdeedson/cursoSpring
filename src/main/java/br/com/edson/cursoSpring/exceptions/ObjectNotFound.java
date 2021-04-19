package br.com.edson.cursoSpring.exceptions;

public class ObjectNotFound extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * instantiates a businessException
	 */
	public ObjectNotFound() {
		super();
	}
	
	/**
	 * Instantiates a businnes exception
	 * @param message
	 */
	public ObjectNotFound(String message) {
		super(message);
	}
	
	public ObjectNotFound( String message, Throwable cause) {
		super(message, cause);
	}

	
}
