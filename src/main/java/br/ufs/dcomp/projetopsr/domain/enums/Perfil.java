package br.ufs.dcomp.projetopsr.domain.enums;

public enum Perfil {
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private Integer id;
	private String valor;
	
	private Perfil(Integer id, String valor) {
		this.id = id;
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public Integer getId() {
		return id;
	}
	
	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		for(Perfil x : Perfil.values()) {
			if(x.getId().equals(cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
