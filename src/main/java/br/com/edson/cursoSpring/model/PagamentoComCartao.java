package br.com.edson.cursoSpring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@Table(name = "tb_pagamento_com_cartao")
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {

	@Column(name = "numero_de_parcelas")
	private Integer numeroDeParcelas;

	
	public PagamentoComCartao() {
		super();
	}
	

	public PagamentoComCartao(Integer estado, Pedido pedido, Integer numeroParcelas) {
		super(estado, pedido);
		this.numeroDeParcelas = numeroParcelas;
	}


	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
}
