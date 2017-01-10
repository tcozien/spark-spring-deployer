package fr.ippon.spark.deployer.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spark-launcher")
public class SparkProperties {

    private String masterDefault;
    private String executorCoresDefault;
    private String executorMemoryDefault;
    private String numExecutorsDefault;
    private String sparkHomeDefault;
    private String logsHome;
    private String appJarDir;
    private String ansibleFilePath;
    private String ansibleHostsPath;
    private String resourceManager;

    public String getMasterDefault() {
        return masterDefault;
    }

    public void setMasterDefault(String masterDefault) {
        this.masterDefault = masterDefault;
    }

    public String getExecutorCoresDefault() {
        return executorCoresDefault;
    }

    public void setExecutorCoresDefault(String executorCoresDefault) {
        this.executorCoresDefault = executorCoresDefault;
    }

    public String getExecutorMemoryDefault() {
        return executorMemoryDefault;
    }

    public void setExecutorMemoryDefault(String executorMemoryDefault) {
        this.executorMemoryDefault = executorMemoryDefault;
    }

    public String getSparkHomeDefault() {
        return sparkHomeDefault;
    }

    public void setSparkHomeDefault(String sparkHomeDefault) {
        this.sparkHomeDefault = sparkHomeDefault;
    }

    public String getLogsHome() {
        return logsHome;
    }

    public void setLogsHome(String logsHome) {
        this.logsHome = logsHome;
    }

    public String getAppJarDir() {
        return appJarDir;
    }

    public void setAppJarDir(String appJarDir) {
        this.appJarDir = appJarDir;
    }

    public String getAnsibleFilePath() {
        return ansibleFilePath;
    }

    public void setAnsibleFilePath(String ansibleFilePath) {
        this.ansibleFilePath = ansibleFilePath;
    }

    public String getAnsibleHostsPath() {
        return ansibleHostsPath;
    }

    public void setAnsibleHostsPath(String ansibleHostsPath) {
        this.ansibleHostsPath = ansibleHostsPath;
    }

    public String getNumExecutorsDefault() {
        return numExecutorsDefault;
    }

    public void setNumExecutorsDefault(String numExecutorsDefault) {
        this.numExecutorsDefault = numExecutorsDefault;
    }

    public String getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(String resourceManager) {
        this.resourceManager = resourceManager;
    }
}
