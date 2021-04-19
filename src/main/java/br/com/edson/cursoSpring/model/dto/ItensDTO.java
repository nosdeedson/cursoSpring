package br.com.edson.cursoSpring.model.dto;

import javax.validation.constraints.NotBlank;

public class ItensDTO {
	
	@NotBlank
	private Integer quantidade;
	
	@NotBlank
	private Integer produtoId;
	
	public ItensDTO() {}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	
	

}
