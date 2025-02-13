package fr.insee.formation.hibernate.archi_hex.domain;

public enum TypeVoie {
	RUE("Rue"), AVENUE("Avenue"), BOULEVARD("Boulevard"), IMPASSE("Impasse"), PLACE("Place"), CHEMIN("Chemin");

	private String libelle;

	private TypeVoie(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
