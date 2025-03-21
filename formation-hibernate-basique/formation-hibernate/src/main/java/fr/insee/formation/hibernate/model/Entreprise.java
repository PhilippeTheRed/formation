package fr.insee.formation.hibernate.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.MapKeyTemporal;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Entreprise {

	@Id
	@GeneratedValue(generator = "hib_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "hib_seq", initialValue = 1, allocationSize = 1)
	private int id;

	private String denomination;

	@Column(length = 9, unique = true, nullable = false)
	private String siren;

	@Embedded
	private Adresse adresse;

	@Column(length = 10)
	private String telephone;

	@Enumerated(EnumType.STRING)
	private FormeJuridique formeJuridique;

	@Temporal(TemporalType.DATE)
	private Date dateCreation;

	@ManyToOne
	@JoinColumn
	private Secteur secteur;

	@OneToMany(mappedBy = "entreprise", cascade=CascadeType.ALL)
	@MapKey(name = "date")
	@MapKeyTemporal(TemporalType.DATE)
	private Map<Date, Declaration> declarations = new HashMap<Date, Declaration>();

	public Map<Date, Declaration> getDeclarations() {
		return Collections.unmodifiableMap(declarations);
	}

	public Declaration addDeclaration(Declaration declaration) {
		declarations.put(declaration.getDate(), declaration);
		declaration.setEntreprise(this);
		return declaration;
	}

	public Declaration removeDeclaration(Declaration declaration) {
		declarations.remove(declaration.getDate(), declaration);
		declaration.setEntreprise(null);
		return declaration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getSiren() {
		return siren;
	}

	public void setSiren(String siren) {
		this.siren = siren;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public FormeJuridique getFormeJuridique() {
		return formeJuridique;
	}

	public void setFormeJuridique(FormeJuridique formeJuridique) {
		this.formeJuridique = formeJuridique;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Secteur getSecteur() {
		return secteur;
	}

	public void setSecteur(Secteur secteur) {
		this.secteur = secteur;
	}

}
