package br.com.edson.cursoSpring.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.edson.cursoSpring.model.enums.StatusPedidoEnum;

@Entity
@Table(name = "tb_pedido")
public class Pedido implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat( pattern = "dd/MM/yyyy HH:mm")
	private Date instante;
	
	@ManyToOne
	@JoinColumn(name = "endereco_de_entrega_id")
	private Endereco enderecoEntrega;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
	private Pagamento pagamento;
	
	
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	private boolean deletado;
	
	@NotNull
	@Column(name = "status_pedido")
	private Integer statusPedido;
	
	
	public Pedido() {}

	public Pedido(Date instante, Endereco enderecoEntrega, Cliente cliente, StatusPedidoEnum statusPedido) {
		super();
		this.instante = instante;
		this.enderecoEntrega = enderecoEntrega;
		this.cliente = cliente;
		this.statusPedido = statusPedido.getCodigo();
	}

	public Double getValorTotal() {
		Double soma =0.0;
		for (ItemPedido itemPedido : itens) {
			soma +=  (itemPedido.getPreco() - itemPedido.getDesconto()) * itemPedido.getQuantidade();
		}
		return soma;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	

	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public Integer getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(Integer statusPedido) {
		this.statusPedido = statusPedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		
		
		builder.append("Prezado cliente: " +getCliente().getNome() );
		builder.append("\nSeu pedido de numero: ");
		builder.append(getId());
		builder.append("\nHora da compra: ");
		builder.append(sdf.format(getInstante()));
		builder.append("\nEntregar em: ");
		builder.append(enderecoEntrega.getLogradouro());
		builder.append(", Bairro: " + enderecoEntrega.getBairro());
		builder.append(", Nº " + enderecoEntrega.getNumero());
		builder.append(", cidade: " + enderecoEntrega.getCidade().getNome());
		builder.append(", CEP: " + enderecoEntrega.getCep());
		builder.append(", " + enderecoEntrega.getCidade().getEstado().getNome());
		builder.append("\nvalor: ");
		builder.append( nf.format( getValorTotal()));
		builder.append("\n statusPedido=");
		builder.append(StatusPedidoEnum.toEnum(getStatusPedido()).getDescricao());
		builder.append("\n");
		builder.append("itens= ");
		builder.append(getItens().toString());
		return builder.toString();
	}

	

}
