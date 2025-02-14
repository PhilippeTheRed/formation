package fr.insee.formation.hibernate.model;

import java.time.Year;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Secteur {

	@Id
	@GeneratedValue
	private int id;

	private String codeNaf;

	private String libelleNomenclature;

	@Transient
	private Set<Entreprise> entreprises = new HashSet<Entreprise>();

	/*
	 * Limitation d'Hibernate : mappedBy ne peut référencer un attribut héritée
	 * avec les héritage @Inheritance (cf
	 * http://chriswongdevblog.blogspot.fr/2009/10/polymorphic-one-to-many-
	 * relationships.html) On peu s'en sortir avec @JoinColumn et @Where.
	 * Avec @MappedSuperclass c'est possible, mais on perd la possibilité de
	 * faire du polymorhpisme...
	 */
	// @OneToMany(mappedBy = "secteur")
	// private Set<IndiceAnnuel> indiceAnnuels = new HashSet<IndiceAnnuel>();
	//
	// @OneToMany(mappedBy = "secteur")
	// private Set<IndiceMensuel> indiceMensuels = new HashSet<IndiceMensuel>();

	@Transient
	private Set<Indice> indices = new HashSet<Indice>();

	public Entreprise addEntreprise(Entreprise entreprise) {
		entreprises.add(entreprise);
		entreprise.setSecteur(this);
		return entreprise;
	}

	public Entreprise removeEntreprise(Entreprise entreprise) {
		entreprises.remove(entreprise);
		entreprise.setSecteur(null);
		return entreprise;
	}

	public Indice addIndice(Indice indice) {
		indices.add(indice);
		indice.setSecteur(this);
		return indice;
	}

	public Indice removeIndice(Indice indice) {
		indices.remove(indice);
		indice.setSecteur(null);
		return indice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodeNaf() {
		return codeNaf;
	}

	public void setCodeNaf(String codeNaf) {
		this.codeNaf = codeNaf;
	}

	public String getLibelleNomenclature() {
		return libelleNomenclature;
	}

	public void setLibelleNomenclature(String libelleNomenclature) {
		this.libelleNomenclature = libelleNomenclature;
	}

	public Set<Entreprise> getEntreprises() {
		return Collections.unmodifiableSet(entreprises);
	}

	// public Set<IndiceAnnuel> getIndiceAnnuels() {
	// return indiceAnnuels;
	// }
	//
	// public void setIndiceAnnuels(Set<IndiceAnnuel> indiceAnnuels) {
	// this.indiceAnnuels = indiceAnnuels;
	// }
	//
	// public Set<IndiceMensuel> getIndiceMensuels() {
	// return indiceMensuels;
	// }
	//
	// public void setIndiceMensuels(Set<IndiceMensuel> indiceMensuels) {
	// this.indiceMensuels = indiceMensuels;
	// }

	public Set<Indice> getIndices() {
		return Collections.unmodifiableSet(indices);
	}

	public Map<Year, IndiceAnnuel> getIndicesAnnuels() {

		Map<Year, IndiceAnnuel> indiceAnnuels = new HashMap<Year, IndiceAnnuel>();

		for (Indice indice : indices) {
			if (indice instanceof IndiceAnnuel) {
				indiceAnnuels.put(((IndiceAnnuel) indice).getYear(), (IndiceAnnuel) indice);
			}
		}

		return indiceAnnuels;
	}

	public Map<YearMonth, IndiceMensuel> getIndicesMensuels() {

		Map<YearMonth, IndiceMensuel> indiceMensuels = new HashMap<YearMonth, IndiceMensuel>();

		for (Indice indice : indices) {

			if (indice instanceof IndiceMensuel) {
				indiceMensuels.put(((IndiceMensuel) indice).getMonth(), (IndiceMensuel) indice);
			}

		}

		return indiceMensuels;

	}

}