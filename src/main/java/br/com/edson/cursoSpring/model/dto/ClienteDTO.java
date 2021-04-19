package br.com.edson.cursoSpring.model.dto;

import br.com.edson.cursoSpring.model.Cliente;

public class ClienteDTO {

	private Integer id;
	
	private String nome;
	
	private String email;
	
	public ClienteDTO() {}

	public ClienteDTO(String nome, String email) {
		super();
		this.nome = nome;
		this.email = email;
	}
	
	public ClienteDTO( Cliente cliente ) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
