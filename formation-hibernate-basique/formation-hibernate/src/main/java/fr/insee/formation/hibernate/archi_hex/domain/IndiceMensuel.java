package fr.insee.formation.hibernate.archi_hex.domain;

import java.time.YearMonth;

import jakarta.persistence.Entity;

@Entity
public class IndiceMensuel extends Indice {

	private YearMonth month;

	public YearMonth getMonth() {
		return month;
	}

	public void setMonth(YearMonth month) {
		this.month = month;
	}
}
