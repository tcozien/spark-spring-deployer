package fr.ippon.spark.deployer.web.controller;

import fr.ippon.spark.deployer.model.UploadedSparkApp;
import fr.ippon.spark.deployer.service.SparkService;
import fr.ippon.spark.deployer.settings.SparkDeployerSettings;
import fr.ippon.spark.deployer.web.controller.validator.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;


@Controller
@EnableConfigurationProperties(SparkDeployerSettings.class)
public class SparkDeployerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SparkDeployerController.class);
    private static final String DEPLOYER_TEMPLATE = "launcher";

    @Autowired
    private FileValidator fileValidator;

    @Autowired
    private SparkService sparkService;

    @Autowired
    private SparkDeployerSettings sparkDeployerSettings;

    @RequestMapping(value = {"/", "/spark/deployer"}, method = RequestMethod.GET)
    public String sparkLauncher(UploadedSparkApp uploadedSparkApp) {
        // Set default properties
        uploadedSparkApp.setMaster(sparkDeployerSettings.getSparkProperties().getMasterDefault());
        uploadedSparkApp.setExecutorCores(sparkDeployerSettings.getSparkProperties().getExecutorCoresDefault());
        uploadedSparkApp.setExecutorMemory(sparkDeployerSettings.getSparkProperties().getExecutorMemoryDefault());
        uploadedSparkApp.setNumExecutors(sparkDeployerSettings.getSparkProperties().getNumExecutorsDefault());
        uploadedSparkApp.setSparkHome(sparkDeployerSettings.getSparkProperties().getSparkHomeDefault());
        return DEPLOYER_TEMPLATE;
    }

    @PostMapping("/spark/deployer")
    public String handleFileUpload(@Valid UploadedSparkApp uploadedSparkApp, BindingResult bindingResult, Model model, @RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        fileValidator.validate(uploadedSparkApp, bindingResult);

        if (bindingResult.hasErrors()) {
            return DEPLOYER_TEMPLATE;
        }

        // Generate Id (used for logging)
        uploadedSparkApp.generateAndSetId();

        // Store file
        boolean fileStored = sparkService.store(file, uploadedSparkApp);

        // Submit job
        boolean appSubmitted = false;
        if (fileStored) {
            appSubmitted = sparkService.submitUploadedSparkApp(uploadedSparkApp);
        }
        model.addAttribute("fileStored", fileStored);
        model.addAttribute("appSubmitted", appSubmitted);
        model.addAttribute("uploadedSparkApp", uploadedSparkApp);
        model.addAttribute("resourceManager", sparkDeployerSettings.getSparkProperties().getResourceManager());
        return DEPLOYER_TEMPLATE;
    }
}