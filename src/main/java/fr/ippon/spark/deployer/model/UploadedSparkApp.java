package fr.ippon.spark.deployer.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadedSparkApp extends SparkApp {

    private MultipartFile file;
    private String appRessourceFile;
    private String inputLogPath;
    private String mainClass;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getAppRessourceFile() {
        return appRessourceFile;
    }

    public void setAppRessourceFile(String appRessourceFile) {
        this.appRessourceFile = appRessourceFile;
    }

    public String getInputLogPath() {
        return inputLogPath;
    }

    public void setInputLogPath(String inputLogPath) {
        this.inputLogPath = inputLogPath;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }
}
