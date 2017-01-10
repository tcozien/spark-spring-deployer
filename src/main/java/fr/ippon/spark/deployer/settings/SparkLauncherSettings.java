package fr.ippon.spark.deployer.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties
@EnableConfigurationProperties(SparkProperties.class)
public class SparkLauncherSettings {

    @Autowired
    private SparkProperties sparkProperties;

    public SparkProperties getSparkProperties() {
        return sparkProperties;
    }

    public void setSparkProperties(SparkProperties sparkProperties) {
        this.sparkProperties = sparkProperties;
    }
}