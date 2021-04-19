package br.com.edson.cursoSpring.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.edson.cursoSpring.model.Categoria;

public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotBlank(message = "Nome é obrigatório.")
	@Size(min = 5, max = 80, message = "No mínimo 5 e no máximo 80 caracteres.")
	private String nome;
	
	public CategoriaDTO() {}
	
	public CategoriaDTO( Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}
	
	public Categoria toEntity() {
		return new Categoria(this.nome);
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
	
	
}
