package br.com.edson.cursoSpring.resources.exception;

public class CampoComErro {
	
	private String campo;
	private String message;
	
	public CampoComErro() {}
	
	public CampoComErro(String campo, String message) {
		this.campo = campo;
		this.message = message;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
