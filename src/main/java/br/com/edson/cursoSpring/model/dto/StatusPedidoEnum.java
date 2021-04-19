package br.com.edson.cursoSpring.model.dto;

public enum StatusPedidoEnum {
	
	ABERTO(1, "Aberto"),
	FINALIZADO(2, "Finalizado"),
	CANCELADO(3, "Cancelado");
	
	private Integer codigo;
	private String descricao;
	
	private StatusPedidoEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static StatusPedidoEnum toEnum( Integer codigo) {
		
		if ( codigo == null)
			return null;
		for ( StatusPedidoEnum pedido : StatusPedidoEnum.values()) {
			if ( pedido.getCodigo().equals(codigo))
				return pedido;
		}
		throw new IllegalArgumentException("Código inválido");
		
	}
	
	

}
