package br.ufs.projetopsr.domain.enums;

public enum TurnoDia {
	MATUTINO(1, "Matutino"),
	VESPERTINO(2, "Vespertino"),
	NOTURNO(3, "Noturno");
	
	private Integer id;
	private String valor;
	
	private TurnoDia(Integer id, String valor) {
		this.id = id;
		this.valor = valor;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getValor() {
		return valor;
	}
	
	public static TurnoDia toEnum(Integer cod) {
		if(cod == null) { 
			return null;
		}
		for(TurnoDia x : TurnoDia.values()) {
			if(x.getId().equals(cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
