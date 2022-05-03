package br.ufs.projetopsr.domain.enums;

public enum CursoSigla {
	EC(1, "Engenharia de Computação"),
	CC(2, "Ciências da Computação"),
	SI(3, "Sistemas de Informação");
	
	private Integer id;
	private String valor;
	
	private CursoSigla(Integer id, String valor) {
		this.id = id;
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public Integer getId() {
		return id;
	}
	
	public static CursoSigla toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		for(CursoSigla x : CursoSigla.values()) {
			if(x.getId().equals(cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
