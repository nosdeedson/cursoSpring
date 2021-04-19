package br.com.edson.cursoSpring.model.dto;

import javax.validation.constraints.NotBlank;

import br.com.edson.cursoSpring.model.Endereco;

public class EnderecoDTO {

	private Integer id;
	
	@NotBlank(message = "logradouro obrigatório")
	private String logradouro;
	
	@NotBlank( message = "numero obrigatório")
	private String numero;
	
	private String complemento;
	
	@NotBlank( message = "bairro obrigatório")
	private String bairro;
	
	@NotBlank( message = "cep obrigatório")
	private String cep;
	
	@NotBlank( message = "cidade obrigatório")
	private String cidade;
	
	@NotBlank( message = "estado obrigatório")
	private String estado;
	
	public EnderecoDTO() {}
	
	public EnderecoDTO(Integer id, String logradouro, String numero, String complemento, String bairro, String cep, String cidade,
			String estado) {
		this.id = id;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.estado = estado;
	}

	public Endereco toEntity() {
		Endereco endereco = new Endereco();
		endereco.setBairro(this.bairro);
		endereco.setCep(this.cep);
		endereco.setComplemento(this.complemento);
		endereco.setLogradouro(this.logradouro);
		endereco.setNumero(this.numero);
		return endereco;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
	
}
