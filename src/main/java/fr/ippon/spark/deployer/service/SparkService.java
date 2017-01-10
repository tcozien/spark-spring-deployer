package fr.ippon.spark.deployer.service;

import fr.ippon.spark.deployer.model.SparkApp;
import fr.ippon.spark.deployer.model.UploadedSparkApp;
import fr.ippon.spark.deployer.settings.SparkDeployerSettings;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Spark launcher service.
 */
@Service
@EnableConfigurationProperties(SparkDeployerSettings.class)
public class SparkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SparkService.class);

    @Autowired
    private SparkDeployerSettings sparkDeployerSettings;

    /**
     * Submit uploaded spark application.
     *
     * @param uploadedSparkApp
     * @return true if submission was successful, false otherwise
     */
    public boolean submitUploadedSparkApp(UploadedSparkApp uploadedSparkApp) {
        LOGGER.info("Try to submit python app on cluster : {}", uploadedSparkApp.toString());

        String logsPath = sparkDeployerSettings.getSparkProperties().getLogsHome();

        // Launch spark-submit
        try {
            String appResourceFile = uploadedSparkApp.getAppRessourceFile();
            LOGGER.debug("App resource file : {}", appResourceFile);

            SparkLauncher sparkLauncher = new SparkLauncher()
                    .setAppResource(appResourceFile)
                    .setMaster(uploadedSparkApp.getMaster())
                    .setMainClass(uploadedSparkApp.getMainClass())
                    .setSparkHome(uploadedSparkApp.getSparkHome())
                    .setVerbose(true)
                    .setDeployMode(uploadedSparkApp.getDeployMode());
            manageSparkClusterParameters(uploadedSparkApp, sparkLauncher);

            Process process = sparkLauncher.launch();
            InputStreamReaderToLog inputStreamReaderToLogError = new InputStreamReaderToLog(process.getErrorStream(), uploadedSparkApp, logsPath, "input.log");
            Thread errorThread = new Thread(inputStreamReaderToLogError, "LOG_INPUT");
            uploadedSparkApp.setInputLogPath(inputStreamReaderToLogError.getLogPath());
            errorThread.start();

        } catch (IOException e) {
            LOGGER.error("Unable to launch Spark Launcher for app {}", uploadedSparkApp.toString());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void manageSparkClusterParameters(SparkApp sparkApp, SparkLauncher sparkLauncher) {
        // Memory by executor
        if (!StringUtils.isEmpty(sparkApp.getExecutorMemory())) {
            LOGGER.debug("Set {} to {}", SparkLauncher.EXECUTOR_MEMORY, sparkApp.getExecutorMemory());
            sparkLauncher.setConf(SparkLauncher.EXECUTOR_MEMORY, sparkApp.getExecutorMemory());
        }
        // Number of cores (tasks) by executor
        if (!StringUtils.isEmpty(sparkApp.getExecutorCores())) {
            LOGGER.debug("Set {} to {}", SparkLauncher.EXECUTOR_CORES, sparkApp.getExecutorCores());
            sparkLauncher.setConf(SparkLauncher.EXECUTOR_CORES, sparkApp.getExecutorCores());
        }
        // Number of executors
        if (!StringUtils.isEmpty(sparkApp.getNumExecutors())) {
            LOGGER.debug("Set {} to {}", "spark.executor.instances", sparkApp.getNumExecutors());
            sparkLauncher.setConf("spark.executor.instances", sparkApp.getNumExecutors());
        }

        // Supervise mode
        if (sparkApp.isSupervise()) {
            sparkLauncher.addSparkArg("--supervise");
        }

        // Jobs arguments
        if (!StringUtils.isEmpty(sparkApp.getJobArguments())) {
            // Split by " "
            Arrays.stream(sparkApp.getJobArguments().split(" ")).forEach(keyValue -> {
                sparkLauncher.addAppArgs(keyValue);
            });
        }

        // Spark arguments
        if (!StringUtils.isEmpty(sparkApp.getSparkArguments())) {
            // Split by ";" and split by " "
            Arrays.stream(sparkApp.getSparkArguments().split(";")).map(argument -> {
                return argument.split(" ");
            }).forEach(keyValue -> {
                if (keyValue.length == 1) {
                    sparkLauncher.addSparkArg(keyValue[0]);
                } else if (keyValue.length == 2) {
                    sparkLauncher.addSparkArg(keyValue[0], keyValue[1]);
                }
            });
        }
    }

    /**
     * Store uploaded file.
     *
     * @param file
     * @param sparkApp
     * @return true if storage was successful, false otherwise
     */
    public boolean store(MultipartFile file, UploadedSparkApp sparkApp) {
        String appResourcePath = sparkDeployerSettings.getSparkProperties().getAppJarDir() + "/" + sparkApp.getId() + "/";
        File directory = new File(appResourcePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            String filePath = appResourcePath + file.getOriginalFilename();
            LOGGER.info("Try to write file {}", filePath);
            Files.write(Paths.get(filePath), file.getBytes());
            sparkApp.setAppRessourceFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
