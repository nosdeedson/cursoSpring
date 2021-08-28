package br.com.edson.cursoSpring.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import br.com.edson.cursoSpring.model.ItemPedido;

public class PedidoDTOOut implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String clienteNome;
	private String cpfOuCnpj;
	private Date instante;
	private Set<ItemPedido> itens;
	
	
	public PedidoDTOOut(String clienteNome, String cpfOuCnpj, Date instante, Set<ItemPedido> itens) {
		this.clienteNome = clienteNome;
		this.cpfOuCnpj = cpfOuCnpj;
		this.instante = instante;
		this.itens = itens;
	}

	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	
	
	
}
