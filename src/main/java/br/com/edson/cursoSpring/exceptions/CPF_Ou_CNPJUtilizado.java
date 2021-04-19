package br.com.edson.cursoSpring.exceptions;

public class CPF_Ou_CNPJUtilizado extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CPF_Ou_CNPJUtilizado(String message) {
		super(message);
	}

}
