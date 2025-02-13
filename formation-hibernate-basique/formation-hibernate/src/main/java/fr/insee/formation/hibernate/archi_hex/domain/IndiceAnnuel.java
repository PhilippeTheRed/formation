package fr.insee.formation.hibernate.archi_hex.domain;

import java.time.Year;

import jakarta.persistence.Entity;

@Entity
public class IndiceAnnuel extends Indice {

	private Year year;

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}
}
