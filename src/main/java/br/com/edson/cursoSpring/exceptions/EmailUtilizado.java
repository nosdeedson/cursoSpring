package br.com.edson.cursoSpring.exceptions;

public class EmailUtilizado extends Exception {
	private static final long serialVersionUID = 1L;
	
	public EmailUtilizado(String message) {
		super(message);
	}

}
