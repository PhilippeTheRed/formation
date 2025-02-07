package fr.insee.formation.hibernate5.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.insee.formation.hibernate5.dao.AbstractGenericDao;
import fr.insee.formation.hibernate5.dao.SecteurDAO;
import fr.insee.formation.hibernate5.model.Declaration;
import fr.insee.formation.hibernate5.model.Entreprise;
import fr.insee.formation.hibernate5.model.Entreprise_;
import fr.insee.formation.hibernate5.model.Secteur;
import fr.insee.formation.hibernate5.model.Secteur_;

@Repository
public class SecteurDaoImpl extends AbstractGenericDao<Secteur, Integer> implements SecteurDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	public SecteurDaoImpl() {
		super(Secteur.class);
	}

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
	public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndices(String codeNaf) {

		String requete = "SELECT secteur FROM Secteur secteur " + " JOIN FETCH secteur.indices indice "
				+ " JOIN FETCH secteur.entreprises entreprise " + " JOIN FETCH entreprise.declarations declaration "
				+ " WHERE secteur.codeNaf = :codeNaf ";

		TypedQuery<Secteur> query = entityManager.createQuery(requete, Secteur.class);

		query.setParameter("codeNaf", codeNaf);

		return query.getSingleResult();
	}

	@Override
	public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesCriteria(String codeNaf) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Secteur> criteria = builder.createQuery(Secteur.class);

		Root<Secteur> root = criteria.from(Secteur.class);

		criteria.select(root);

		criteria.where(builder.equal(root.get(Secteur_.codeNaf), codeNaf));

		/*
		 * On récupérer les indices, entreprises, et déclarations
		 */
		root.fetch(Secteur_.indices, JoinType.INNER);
		
		Fetch<Secteur, Entreprise> fetchEntreprise = root.fetch(Secteur_.entreprises, JoinType.INNER);
		
		Fetch<Entreprise, Declaration> fetchDeclarations = fetchEntreprise.fetch(Entreprise_.declarations, JoinType.INNER);
		
		return entityManager.createQuery(criteria).getSingleResult();
	}

}
