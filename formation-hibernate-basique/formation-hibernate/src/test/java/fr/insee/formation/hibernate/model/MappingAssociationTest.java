package fr.insee.formation.hibernate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.insee.formation.hibernate.config.AbstractTest;
import fr.insee.formation.hibernate.dao.SecteurDAO;
import fr.insee.formation.hibernate.util.JeuxTestUtil;
import jakarta.transaction.Transactional;

/**
 * Test pour le TP5
 *
 */
public class MappingAssociationTest extends AbstractTest {

	@Autowired
	private JeuxTestUtil jeuxTestUtil;

	@Autowired
	private SecteurDAO secteurDAO;

	@BeforeAll
	public void testMappingAssociation() {

		jeuxTestUtil.creerJeuxMappingAssociation();

	}

	@Test
	@Transactional
	public void testSecteur() {

		Secteur secteur = secteurDAO.findByCodeNaf("1104Z");

		assertEquals(3, secteur.getEntreprises().size(), "Il doit y avoir 3 entreprise");

		for (Entreprise entreprise : secteur.getEntreprises()) {

			assertEquals(12, entreprise.getDeclarations().size(), "l'entreprise doit avoir 12 declaration");

		}

		assertEquals(13, secteur.getIndices().size(), "Le secteur doit avoir 13 indices");

	}

}
