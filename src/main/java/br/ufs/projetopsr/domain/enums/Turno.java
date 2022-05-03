package br.ufs.projetopsr.domain.enums;

public enum Turno {
	MATUTINO(1, "Matutino"),
	VESPERTINO(2, "Vespertino"),
	NOTURNO(3, "Noturno");
	
	private Integer id;
	private String valor;
	
	private Turno(Integer id, String valor) {
		this.id = id;
		this.valor = valor;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getValor() {
		return valor;
	}
	
	public static Turno toEnum(Integer cod) {
		if(cod == null) { 
			return null;
		}
		for(Turno x : Turno.values()) {
			if(x.getId().equals(cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
