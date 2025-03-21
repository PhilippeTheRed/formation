package fr.insee.formation.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import fr.insee.formation.hibernate.dao.SecteurDAO;
import fr.insee.formation.hibernate.model.Secteur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class SecteurDaoImpl implements SecteurDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Secteur find(int id) {

		return entityManager.find(Secteur.class, id);

	}

	@Override
	public Secteur findByCodeNaf(String codeNaf) {
		return entityManager.createQuery("SELECT sect FROM Secteur sect WHERE sect.codeNaf = :codeNaf", Secteur.class)
				.setParameter("codeNaf", codeNaf).getSingleResult();
	}

	@Override
	public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesJPQL(String codeNaf) {

		String requete = "SELECT secteur FROM Secteur secteur " + " JOIN FETCH secteur.indices indice "
				+ " JOIN FETCH secteur.entreprises entreprise " + " JOIN FETCH entreprise.declarations declaration "
				+ " WHERE secteur.codeNaf = :codeNaf ";

		TypedQuery<Secteur> query = entityManager.createQuery(requete, Secteur.class);

		query.setParameter("codeNaf", codeNaf);

		return query.getSingleResult();
	}

	@Override
	public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesCriteria(String codeNaf) {

		//TODO : TP8 Réaliser la méthode de récupération du secteurs avec ses associations en Criteria
		
		return null;
	}

}

