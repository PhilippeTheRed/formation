package fr.insee.formation.hibernate.config;

import org.junit.Test;
import org.testcontainers.containers.GenericContainer;

public class PodmanContainerTest {

    @Test
    public void testPodmanContainer() {
        try (GenericContainer<?> container = new GenericContainer<>("alpine:latest")
                .withCommand("echo", "Hello, Podman!")) {
            container.start();
            System.out.println("Container started!");
        }
    }
}