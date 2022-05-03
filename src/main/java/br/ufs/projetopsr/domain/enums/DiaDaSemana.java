package br.ufs.projetopsr.domain.enums;

public enum DiaDaSemana {
	DOMINGO(1, "Domingo"),
	SEGUNDA(2, "Segunda"),
	TERCA(3, "Terca"),
	QUARTA(4, "Quarta"),
	QUINTA(5, "Quinta"),
	SEXTA(6, "Sexta"),
	SABADO(7, "Sabado");
	
	private Integer id;
	private String valor;
	
	private DiaDaSemana(Integer id, String valor) {
		this.id = id;
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public Integer getId() {
		return id;
	}
	
	public static DiaDaSemana toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		for(DiaDaSemana x : DiaDaSemana.values()) {
			if(x.getId().equals(cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
