package fr.ippon.spark.deployer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = "fr.ippon.spark")
public class SparkLauncherApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(SparkLauncherApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SparkLauncherApplication.class, args);
    }
}