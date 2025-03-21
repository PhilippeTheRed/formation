package fr.insee.formation.hibernate.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;
import java.time.YearMonth;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.insee.formation.hibernate.config.AbstractTest;
import fr.insee.formation.hibernate.model.Indice;
import fr.insee.formation.hibernate.model.IndiceAnnuel;
import fr.insee.formation.hibernate.model.IndiceMensuel;
import fr.insee.formation.hibernate.util.JeuxTestUtil;

/**
 * Test pour le TP4 mapping de l'héritage
 */
public class IndiceDAOTest extends AbstractTest{

	@Autowired
	private IndiceDAO indiceDAO;

	@Autowired
	private JeuxTestUtil jeuxTestUtil;

	@Test
	public void testMappingIndices() {

		int identifiant1 = jeuxTestUtil.creerIndiceAnnuel().getId();

		int indentifiant2 = jeuxTestUtil.creerIndiceMensuel().getId();

		IndiceAnnuel indiceAnnuel = indiceDAO.findIndiceAnnuel(identifiant1);

		assertEquals(Double.valueOf(10), indiceAnnuel.getValeur(), "L'indice vaut 10");

		assertEquals(indiceAnnuel.getYear().toString(), "2017", "L'annuel est 2017");

		IndiceMensuel indiceMensuel = indiceDAO.findIndiceMensuel(indentifiant2);

		assertEquals(YearMonth.of(2017, Month.DECEMBER), indiceMensuel.getMonth(), "Le mois doit être decemnbre");

		assertEquals(Double.valueOf(10.4d), indiceMensuel.getValeur(), "L'indice doit valoir 10.4");

		Set<Indice> indices = indiceDAO.findAll();

		assertEquals(2, indices.size(), "On doit récupérer les 2 indices");

	}

}
