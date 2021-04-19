package br.com.edson.cursoSpring.exceptions;

import org.springframework.http.HttpStatus;

public class AmazonException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;

	public AmazonException(String message, Throwable cause, HttpStatus status) {
		super(message, cause);
		this.status = status;
	}

	public AmazonException(String message) {
		super(message);
	}
	
	public AmazonException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	
	
	

}
