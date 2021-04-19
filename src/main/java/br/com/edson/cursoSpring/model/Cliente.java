package br.com.edson.cursoSpring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.edson.cursoSpring.model.enums.PerfilEnum;
import br.com.edson.cursoSpring.model.enums.TipoCliente;

@Entity
@Table(name = "tb_cliente")
public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	
	@Column(name = "cpf_ou_cnpj")
	private String cpfOuCnpj;
	
	private Integer tipo;
	
	@JsonIgnore
	private String senha;
	
	@OneToMany(mappedBy = "cliente", cascade = javax.persistence.CascadeType.ALL)
	//@Cascade(CascadeType.DELETE)
	private List<Endereco> enderecos = new ArrayList<>();

	
	@ElementCollection
	@CollectionTable(name = "tb_telefones")
	private Set<String> telefones = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	@ElementCollection( fetch = FetchType.EAGER)
	@CollectionTable( name = "tb_perfil")
	private Set<Integer> perfis = new HashSet<>();
	public Cliente() {
		this.addPerfil(PerfilEnum.CLIENTE);
	}

	/**
	 * todo usuário do sistem vai ser cliente
	 * @param nome
	 * @param email
	 * @param cpfOuCnpj
	 * @param tipo
	 */
	public Cliente(String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo == null) ? null : tipo.getCodigo();
		this.addPerfil(PerfilEnum.CLIENTE);
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(this.tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCodigo();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Set<PerfilEnum> getPerfis(){
		// toSet converte para conjunto
		return (Set<PerfilEnum>) perfis.stream().map(cod -> PerfilEnum.toEnum(cod))
				.collect(Collectors.toSet());
	}
	
	public void addPerfil( PerfilEnum perfil) {
		this.perfis.add(perfil.getCodigo());
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
