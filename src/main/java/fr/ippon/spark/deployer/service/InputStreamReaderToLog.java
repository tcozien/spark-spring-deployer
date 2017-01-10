package fr.ippon.spark.deployer.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import fr.ippon.spark.deployer.model.SparkApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class InputStreamReaderToLog implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputStreamReaderToLog.class);
    private Logger inputLogger;
    private BufferedReader reader;
    private SparkApp sparkApp;
    private String directoryLogPath;
    private String logName;
    private String logPath;

    public InputStreamReaderToLog(InputStream is, SparkApp sparkApp, String directoryLogPath, String logName) {
        this.directoryLogPath = directoryLogPath;
        this.sparkApp = sparkApp;
        this.logName = logName;
        this.inputLogger = createLogFile();
        this.reader = new BufferedReader(new InputStreamReader(is));
    }

    private Logger createLogFile() {
        // Create directory
        File directoryFile = new File(directoryLogPath);
        if (!directoryFile.exists()) {
            LOGGER.debug("Create folder {}", directoryLogPath);
            directoryFile.mkdir();
        }

        // Create spark app directory
        String sparkAppFilePath = directoryLogPath + sparkApp.getId() + "/";
        File sparkAppFile = new File(sparkAppFilePath);
        if (!sparkAppFile.exists()) {
            LOGGER.debug("Create sparkAppFilePath {}", sparkAppFilePath);
            sparkAppFile.mkdir();
        }

        // Creates loggers
        this.logPath = sparkAppFilePath + logName;
        // Distinct logger for avoiding to log every job into the same file
        return createLoggerFor(this.sparkApp.getId() + "_spark", logPath);
    }

    private Logger createLoggerFor(String name, String file) {
        LOGGER.debug("Create logger {}", file);

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        PatternLayoutEncoder ple = new PatternLayoutEncoder();
        ple.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        ple.setContext(lc);
        ple.start();

        FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
        fileAppender.setFile(file);
        fileAppender.setEncoder(ple);
        fileAppender.setContext(lc);
        fileAppender.start();

        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(name);
        logger.addAppender(fileAppender);
        logger.setLevel(Level.DEBUG);
        logger.setAdditive(false);
        return logger;
    }

    public void run() {
        try {
            LOGGER.debug("For inputLogger {}", inputLogger.getName());
            LOGGER.debug("Start thread {}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
            String line = reader.readLine();
            while (line != null) {
                inputLogger.info(line);
                line = reader.readLine();
            }
            LOGGER.debug("Close thread {}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogPath() {
        return logPath;
    }
}