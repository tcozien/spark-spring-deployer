package fr.ippon.spark.deployer.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Scala/Java Spark application.
 */
public class ScalaSparkApp extends SparkApp {

    @Size(max = 250)
    @NotNull
    @NotEmpty
    private String mainClass;
    @Size(max = 250)
    @NotNull
    @NotEmpty
    private String appResource;
    @Size(max = 250)
    @NotNull
    @NotEmpty
    private String appResourceVersion;
    private String appResourceVersionSnapshot;

    private String inputLogPath;
    private String inputAnsiblePath;

    public ScalaSparkApp() {
        super();
        this.appResource = "";
        this.appResourceVersion = "";
        this.mainClass = "";
    }

    public String getMainClass() {
        return mainClass;
    }

    public String getAppResource() {
        return appResource;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public void setAppResource(String appResource) {
        this.appResource = appResource;
    }

    public String getInputLogPath() {
        return inputLogPath;
    }

    public void setInputLogPath(String inputLogPath) {
        this.inputLogPath = inputLogPath;
    }

    public String getInputAnsiblePath() {
        return inputAnsiblePath;
    }

    public void setInputAnsiblePath(String inputAnsiblePath) {
        this.inputAnsiblePath = inputAnsiblePath;
    }

    public String getAppResourceVersion() {
        return appResourceVersion;
    }

    public void setAppResourceVersion(String appResourceVersion) {
        this.appResourceVersion = appResourceVersion;
    }

    public String getAppResourceVersionSnapshot() {
        return appResourceVersionSnapshot;
    }

    public void setAppResourceVersionSnapshot(String appResourceVersionSnapshot) {
        this.appResourceVersionSnapshot = appResourceVersionSnapshot;
    }

    public boolean isDevelopment() {
        return this.appResourceVersion.contains("SNAPSHOT");
    }

    public String getAppRessourcePath() {
        return this.appResource + "-" + this.appResourceVersion;
    }
}
