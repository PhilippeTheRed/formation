package fr.insee.formation.hibernate.services;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import fr.insee.formation.hibernate.config.AbstractTest;
import fr.insee.formation.hibernate.dao.SecteurDAO;
import fr.insee.formation.hibernate.model.Secteur;
import fr.insee.formation.hibernate.util.JeuxTestUtil;
import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;

/**
 * Classe de test pour le TP8
 */
@SpringBootTest(properties = { "activate.datasource-proxy=true" })
public class SecteurServicesPerformancesTestCriteria extends AbstractTest{

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
	public void testCalculerIndicesCriteria() {

		/*
		 * on remet à zéro le compteur de requête
		 */
		QueryCountHolder.clear();

		secteurServices.calculerIndicesSecteurByCodeNafRequeteCriteria("1104Z");

		QueryCount queryCount = QueryCountHolder.getGrandTotal();

		assertEquals(1, queryCount.getSelect(), "Il ne doit y avoir qu'une requête");

		transactionTemplate.execute(new TransactionCallback<Object>() {

			public Object doInTransaction(TransactionStatus arg0) {

				Secteur secteur = secteurDAO.findByCodeNaf("1104Z");

				assertEquals(new Double(468),
						secteur.getIndicesAnnuels().get(Year.parse("2016")).getValeur(), "L'indice annuel de 2016 doit valoir 1170");

				assertEquals(new Double(72),
						secteur.getIndicesMensuels().get(YearMonth.of(2016, Month.DECEMBER)).getValeur(), "L'indice mensuel de décembre 2016 doit valoir 180");

				return null;
			}
		});

	}

}
