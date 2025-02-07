package fr.insee.formation.hibernate.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.insee.formation.hibernate.config.AbstractTest;
import fr.insee.formation.hibernate.model.Entreprise;
import fr.insee.formation.hibernate.util.JeuxTestUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class EntrepriseDAOCRUDTest extends AbstractTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EntrepriseDAO entrepriseDAO;

	@Autowired
	private JeuxTestUtil jeuxTestUtil;

	@BeforeEach
	public void init() {

		jeuxTestUtil.insererJeuxVolumeReduit();

	}

	@Test
	public void testInsert() {

		Entreprise entreprise = new Entreprise();

		entreprise.setDenomination("Nouvelle entreprise à créer");

		entreprise.setSiren("000000000");

		entrepriseDAO.persist(entreprise);

		List<Entreprise> entreprises = entityManager.createQuery("SELECT ent FROM Entreprise ent", Entreprise.class)
				.getResultList();

		assertEquals(4, entreprises.size(), "Il doit y avoir 4 entreprises");

	}

	@Test
	public void testUpdate() {

		entrepriseDAO.upperDenomination();

		Entreprise entreprise = entityManager.find(Entreprise.class, 2);

		assertEquals(entreprise.getDenomination().toUpperCase(), entreprise.getDenomination(),
				"La dénomination doit être en majuscule");

	}

	@Test
	public void testRemove() {

		entrepriseDAO.removeEntrepriseById(3);

		List<Entreprise> entreprises = entityManager.createQuery("SELECT ent FROM Entreprise ent", Entreprise.class)
				.getResultList();

		assertEquals(2, entreprises.size(), "Il doit y avoir 2 entreprises");
	}

}
