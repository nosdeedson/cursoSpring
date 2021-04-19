package br.com.edson.cursoSpring.resources.exception;

import java.io.Serializable;

public class StandardError implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String agora;
	private Integer status;
	private String error;
	private String message;
	private String path;
	
	public StandardError() {}
	
	public StandardError(String agora, Integer status, String error, String message, String path) {
		this.agora = agora;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public String getAgora() {
		return agora;
	}

	public void setAgora(String agora) {
		this.agora = agora;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
	
}
