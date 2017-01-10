package fr.ippon.spark.deployer;

import com.cdiscount.search.settings.AbstractSimpleSpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = "com.cdiscount.bigdata")
@EnableSwagger2
public class SparkLauncherApplication extends AbstractSimpleSpringBootApplication<SparkLauncherApplication> {

    public static final Logger LOGGER = LoggerFactory.getLogger(SparkLauncherApplication.class);

    public static void main(String[] args) {
        new SparkLauncherApplication().initApp(args, SparkLauncherApplication.class);
    }
}