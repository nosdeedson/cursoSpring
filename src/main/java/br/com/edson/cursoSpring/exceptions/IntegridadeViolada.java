package br.com.edson.cursoSpring.exceptions;

public class IntegridadeViolada extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * instantiates a businessException
	 */
	public IntegridadeViolada() {
		super();
	}
	
	/**
	 * Instantiates a businnes exception
	 * @param message
	 */
	public IntegridadeViolada(String message) {
		super(message);
	}
	
	public IntegridadeViolada( String message, Throwable cause) {
		super(message, cause);
	}

	
}
