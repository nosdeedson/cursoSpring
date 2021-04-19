package br.com.edson.cursoSpring.model.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.edson.cursoSpring.model.Cliente;
import br.com.edson.cursoSpring.model.enums.TipoCliente;
import br.com.edson.cursoSpring.services.validation.ClienteInsert;

@ClienteInsert
public class CadastroClienteDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome obrigatório")
	@Size(min = 5, max = 80, message = "Mínimo 5 máximo 80 caracteres")
	private String nome;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank( message = "Informar o cpf ou cnpj")
	@Column(name = "cpf_ou_cnpj")
	private String cpfOuCnpj;
	
	@NotNull
	private String tipo;
	
	@NotNull
	private String logradouro;
	
	@NotBlank( message = "numero obrigatório")
	private String numero;
	
	private String complemento;
	
	@NotBlank( message = "bairro obrigatório")
	private String bairro;
	
	@NotBlank( message = "cep obrigatório")
	private String cep;
	
	@NotNull( message = "cidade obrigatório")
	private Integer cidadeId;
	
	@NotNull( message = "estado obrigatório")
	private Integer estadoId;
	
	@NotBlank
	private String telefone1;
	
	private String telefone2;
	
	private String telefone3;
	
	@NotEmpty(message = "Senha é obrigatória.")
	private String senha;

	@NotEmpty(message = "Campo confirmação de Senha é obrigatória.")
	private String confirmeSenha;
	
	public CadastroClienteDTO() {}
	
	
	public CadastroClienteDTO(
			String nome,
			String email, 
			String cpfOuCnpj,
			String tipo,
			String logradouro,
			String numero, 
			String complemento,
			String bairro, 
			String cep,
			Integer cidadeId,
			Integer estadoId,
			String telefone1,
			String senha,
			String confirmeSenha) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = tipo;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.cidadeId = cidadeId;
		this.estadoId = estadoId;
		this.telefone1 = telefone1;
		this.senha = senha;
		this.confirmeSenha = confirmeSenha;
	}



	public Cliente toCliente() {
		Cliente cliente = new Cliente();
		cliente.setCpfOuCnpj(this.cpfOuCnpj);
		cliente.setEmail(this.email);
		cliente.setNome(this.nome);
		cliente.setTipo(TipoCliente.toEnum(Integer.parseInt(tipo)));
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		cliente.setSenha(encoder.encode(this.email));
		return cliente;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmeSenha() {
		return confirmeSenha;
	}

	public void setConfirmeSenha(String confirmeSenha) {
		this.confirmeSenha = confirmeSenha;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

	public Integer getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Integer estadoId) {
		this.estadoId = estadoId;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}
	
	

}
