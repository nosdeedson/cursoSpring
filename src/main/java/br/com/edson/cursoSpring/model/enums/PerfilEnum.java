package br.com.edson.cursoSpring.model.enums;

import br.com.edson.cursoSpring.exceptions.BusinessException;

public enum PerfilEnum {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private String descricao;
	private Integer codigo;
	
	private PerfilEnum(Integer codigo, String descricao) {
		this.descricao = descricao;
		this.codigo = codigo;
	}
	
	public static PerfilEnum toEnum( Integer codigo) {
		if ( codigo == null) {
			return null;
		}
		
		for ( PerfilEnum perfil : PerfilEnum.values()) {
			if( perfil.getCodigo().equals(codigo)) {
				return perfil;
			}
		}
		throw new BusinessException("Código: " +  codigo + " inválido.");
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	

}
