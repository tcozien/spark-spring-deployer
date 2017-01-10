package fr.ippon.spark.deployer.model;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

public class SparkAppTest {

    @Test
    public void canGenerateAndSetId_TeamEmpty() {
        // Given
        SparkApp sparkApp = new SparkApp();

        // When
        try {
            sparkApp.generateAndSetId();
            fail("team must not be null");
        } catch (Exception e) {
        }
    }

    @Test
    public void canGenerateAndSetId() {
        // Given
        SparkApp sparkApp = new SparkApp();
        sparkApp.setTeam("team");

        // When
        sparkApp.generateAndSetId();

        // Assert
        assertThat(sparkApp.getId()).isNotEmpty();
        assertThat(sparkApp.getId()).startsWith("team_");
    }

}
