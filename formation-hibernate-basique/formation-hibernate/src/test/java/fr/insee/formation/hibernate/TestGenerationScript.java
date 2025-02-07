package fr.insee.formation.hibernate;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.insee.formation.hibernate.config.AbstractTest;

public class TestGenerationScript extends AbstractTest{

	@Test
	public void test() {
		assertTrue(true);
	}

}
