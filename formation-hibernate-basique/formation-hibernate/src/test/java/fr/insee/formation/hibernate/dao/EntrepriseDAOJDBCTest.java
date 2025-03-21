package fr.insee.formation.hibernate.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.insee.formation.hibernate.config.AbstractTest;
import fr.insee.formation.hibernate.model.Entreprise;
import fr.insee.formation.hibernate.model.TypeVoie;

/**
 * Classe de test pour le TP1
 */
public class EntrepriseDAOJDBCTest extends AbstractTest{

	@Autowired
	private EntrepriseDAO entrepriseDAO;

	@Test
	public void testFindAllOrderByDateCreation() {

		List<Entreprise> entreprises = entrepriseDAO.findAllOrderByDateCreation();

		assertEquals(3, entreprises.size(), "Il doit y avoir 3 entreprises");

		Entreprise entreprise1 = entreprises.get(0);

		assertEquals(entreprise1.getId(), 3);

		assertEquals("Le p'tit nantais", entreprise1.getDenomination());

		assertEquals("Alfred Alfred", entreprise1.getAdresse().getNomVoie());

		assertEquals("345678912", entreprise1.getSiren());

		Entreprise entreprise3 = entreprises.get(2);

		assertEquals(entreprise3.getId(), 2);

		assertEquals("Confiserie", entreprise3.getDenomination());

		assertEquals("RENNES", entreprise3.getAdresse().getVille());

		assertEquals(TypeVoie.BOULEVARD, entreprise3.getAdresse().getTypeVoie());

	}

}
