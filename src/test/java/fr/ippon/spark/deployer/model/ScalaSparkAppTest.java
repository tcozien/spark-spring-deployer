package fr.ippon.spark.deployer.model;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ScalaSparkAppTest {

    @Test
    public void isDevelopment_true() {
        // Given
        ScalaSparkApp sparkApp = new ScalaSparkApp();
        sparkApp.setAppResourceVersion("0.0.2-SNAPSHOT");

        // When
        boolean isDev = sparkApp.isDevelopment();

        // Assert
        assertThat(isDev).isTrue();
    }

    @Test
    public void isDevelopment_false() {
        // Given
        ScalaSparkApp sparkApp = new ScalaSparkApp();
        sparkApp.setAppResourceVersion("0.0.2");

        // When
        boolean isDev = sparkApp.isDevelopment();

        // Assert
        assertThat(isDev).isFalse();
    }

}

