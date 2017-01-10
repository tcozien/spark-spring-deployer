package fr.ippon.spark.deployer.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Spark application.
 */
public class SparkApp {

    private String id;
    @Size(max = 250)
    @NotNull
    @NotEmpty
    private String team;
    @Size(max = 250)
    @NotNull
    @NotEmpty
    private String master;
    @Size(max = 10)
    private String executorMemory;
    @Size(max = 10)
    private String executorCores;
    @Size(max = 10)
    private String numExecutors;
    @NotNull
    @NotEmpty
    private String sparkHome;
    @NotNull
    @NotEmpty
    @Size(max = 20)
    private String deployMode;
    private String applicationArguments;
    private boolean supervise;
    private String sparkArguments;
    private String jobArguments;

    public SparkApp() {
        this.id = "";
        this.team = "";
        this.master = "";
        this.executorMemory = "";
        this.executorCores = "";
        this.numExecutors = "";
        this.deployMode = "";
        this.applicationArguments = "";
        this.jobArguments = "";
    }

    public void generateAndSetId() {
        Assert.hasLength(this.team);

        StringBuilder sb = new StringBuilder();
        sb.append(this.team.toLowerCase());
        sb.append("_");
        Date date = new Date();
        sb.append(date.getTime());
        this.id = sb.toString();
    }

    public String getTeam() {
        return team;
    }

    public String getMaster() {
        return master;
    }

    public String getExecutorMemory() {
        return executorMemory;
    }

    public String getExecutorCores() {
        return executorCores;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public void setExecutorMemory(String executorMemory) {
        this.executorMemory = executorMemory;
    }

    public void setExecutorCores(String executorCores) {
        this.executorCores = executorCores;
    }

    public String getSparkHome() {
        return sparkHome;
    }

    public void setSparkHome(String sparkHome) {
        this.sparkHome = sparkHome;
    }

    public String getDeployMode() {
        return deployMode;
    }

    public void setDeployMode(String deployMode) {
        this.deployMode = deployMode;
    }

    public String getApplicationArguments() {
        return applicationArguments;
    }

    public void setApplicationArguments(String applicationArguments) {
        this.applicationArguments = applicationArguments;
    }

    public boolean isSupervise() {
        return supervise;
    }

    public void setSupervise(boolean supervise) {
        this.supervise = supervise;
    }

    public String getNumExecutors() {
        return numExecutors;
    }

    public void setNumExecutors(String numExecutors) {
        this.numExecutors = numExecutors;
    }

    public String getSparkArguments() {
        return sparkArguments;
    }

    public void setSparkArguments(String sparkArguments) {
        this.sparkArguments = sparkArguments;
    }

    public String getJobArguments() {
        return jobArguments;
    }

    public void setJobArguments(String jobArguments) {
        this.jobArguments = jobArguments;
    }
}
