package br.com.edson.cursoSpring.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@Table(name = "tb_pagamento_com_boleto")
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento{

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_pagamento")
	private LocalDateTime dataPagamento;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_vencimento")
	private LocalDateTime dataVencimento;
	
	public PagamentoComBoleto() {
		super();
	}
	
	public PagamentoComBoleto(Integer estado, Pedido pedido, LocalDateTime dataPagamento, LocalDateTime dataVencimento) {
		super(estado, pedido);
		this.dataPagamento= dataPagamento;
		this.dataVencimento= dataVencimento;
	}


	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public LocalDateTime getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDateTime dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	
}
