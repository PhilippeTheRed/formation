package fr.insee.formation.hibernate.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import fr.insee.formation.hibernate.config.AbstractTest;
import fr.insee.formation.hibernate.dao.SecteurDAO;
import fr.insee.formation.hibernate.model.Secteur;
import fr.insee.formation.hibernate.util.JeuxTestUtil;

public class SecteurServicesTest extends AbstractTest {

	@Autowired
	private JeuxTestUtil jeuxTestUtil;

	@Autowired
	private SecteurServices secteurServices;

	@Autowired
	private SecteurDAO secteurDAO;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@BeforeAll
	public void testMappingAssociation() {
		jeuxTestUtil.creerJeuxMappingAssociation();
	}

	@Test
	public void testCalculerIndices() {

		secteurServices.calculerIndicesSecteurByCodeNaf("1104Z");

		transactionTemplate.execute(new TransactionCallback<Object>() {

			public Object doInTransaction(TransactionStatus arg0) {

				Secteur secteur = secteurDAO.findByCodeNaf("1104Z");

				assertEquals(new Double(468), secteur.getIndicesAnnuels().get(Year.parse("2016")).getValeur(),
						"L'indice annuel de 2016 doit valoir 468");

				assertEquals(new Double(72),
						secteur.getIndicesMensuels().get(YearMonth.of(2016, Month.DECEMBER)).getValeur(),
						"L'indice mensuel de décembre 2016 doit valoir 72");

				return null;
			}
		});

	}

}
