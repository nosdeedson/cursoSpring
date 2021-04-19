package br.com.edson.cursoSpring.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<CampoComErro> camposComErro = new ArrayList<CampoComErro>();

	public ValidationError() {}
	
	public ValidationError(String agora, Integer status, String error, String message, String path) {
		super(agora, status, error, message, path);
	}

	public List<CampoComErro> getCamposComErro() {
		return camposComErro;
	}

	public void setCamposComErro(List<CampoComErro> camposComErro) {
		this.camposComErro = camposComErro;
	}
	
	
	

}
