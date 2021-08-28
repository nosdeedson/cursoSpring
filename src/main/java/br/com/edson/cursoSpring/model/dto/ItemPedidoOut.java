package br.com.edson.cursoSpring.model.dto;

import java.io.Serializable;

public class ItemPedidoOut implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Double desconto;


	public ItemPedidoOut(Double desconto) {
		
		this.desconto = desconto;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	
}
