package fr.ippon.spark.deployer.service;

import fr.ippon.spark.deployer.settings.SparkDeployerSettings;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Flush old log files.
 */
@Service
@EnableConfigurationProperties(SparkDeployerSettings.class)
public class FlushService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlushService.class);

    @Autowired
    private SparkDeployerSettings sparkDeployerSettings;

    /**
     * Remove old of 7 days files.
     * @throws IOException
     */
    @Scheduled(cron = "0 0/30 * * * *")
    public void removeOldFiles() throws IOException {

        final Date currentDate = new Date(System.currentTimeMillis());
        Duration duration = Duration.ofDays(1);

        String logsPath = sparkDeployerSettings.getSparkProperties().getLogsHome();
        final File logDirectory = new File(logsPath);
        final AtomicInteger deletedLogsCounter = new AtomicInteger(0);
        if (logDirectory.isDirectory()) {
            Files.walkFileTree(logDirectory.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs)
                        throws IOException {
                    if (path.compareTo(logDirectory.toPath()) != 0 && currentDate.getTime() - attrs.creationTime().toMillis() > duration.toMillis()) {
                        FileUtils.deleteDirectory(path.toFile());
                        deletedLogsCounter.getAndIncrement();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        if (deletedLogsCounter.intValue() > 0){
            LOGGER.info("{} logs deleted", deletedLogsCounter.intValue());
        }

        LOGGER.info("Remove old resources files");
        String resourcePath = sparkDeployerSettings.getSparkProperties().getAppJarDir();
        final File resourceDirectory = new File(resourcePath);
        final AtomicInteger deletedResourceCounter = new AtomicInteger(0);
        if (resourceDirectory.isDirectory()) {
            Files.walkFileTree(resourceDirectory.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs)
                        throws IOException {
                    if (path.compareTo(resourceDirectory.toPath()) != 0 && currentDate.getTime() - attrs.creationTime().toMillis() > duration.toMillis()) {
                        FileUtils.deleteDirectory(path.toFile());
                        deletedResourceCounter.getAndIncrement();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        if (deletedResourceCounter.intValue() > 0){
            LOGGER.info("{} resources deleted", deletedResourceCounter.intValue());
        }

    }

}
