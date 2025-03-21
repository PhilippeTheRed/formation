package fr.insee.formation.hibernate5.dao;

import fr.insee.formation.hibernate5.model.Secteur;

public interface SecteurDAO extends GenericDao<Secteur, Integer>{

	public Secteur find(int id);
	
	public Secteur findByCodeNaf(String codeNaf);

	public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndices(String codeNaf);

	public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesCriteria(String codeNaf);

}
