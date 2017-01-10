package fr.ippon.spark.deployer.web.controller.validator;

import fr.ippon.spark.deployer.model.UploadedSparkApp;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FileValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UploadedSparkApp.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UploadedSparkApp bucket = (UploadedSparkApp) target;
        if (bucket.getFile() != null && bucket.getFile().isEmpty()) {
            errors.rejectValue("file", "file.empty");
        }
    }
}
