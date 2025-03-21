package fr.insee.formation.hibernate.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Indice {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn
	private Secteur secteur;

	private Double valeur;
	
	private Instant derniereMaj;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Secteur getSecteur() {
		return secteur;
	}

	public void setSecteur(Secteur secteur) {
		this.secteur = secteur;
	}

	public Double getValeur() {
		return valeur;
	}

	public void setValeur(Double valeur) {
		this.valeur = valeur;
	}

	public Instant getDerniereMaj() {
		return derniereMaj;
	}

	public void setDerniereMaj(Instant derniereMaj) {
		this.derniereMaj = derniereMaj;
	}

}
