package br.com.edson.cursoSpring.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

public class PedidoDTO {
	
	@NotBlank
	private Integer clienteId;
	
	@NotBlank
	private Integer enderecoEntregaId;
	
	@NotBlank
	private Integer numeroParcelas;
	
	@NotBlank
	private String tipoPagamento;
	
	private List<ItensDTO> itens = new ArrayList<>();
	
	public PedidoDTO() {}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Integer getEnderecoEntregaId() {
		return enderecoEntregaId;
	}

	public void setEnderecoEntregaId(Integer enderecoEntregaId) {
		this.enderecoEntregaId = enderecoEntregaId;
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagemento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public List<ItensDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItensDTO> itens) {
		this.itens = itens;
	}
	
	

}
