package fr.ippon.spark.deployer.web.controller;

import io.swagger.annotations.Api;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
@Api(tags = "Log")
public class LogController {

    @RequestMapping(value = "/spark/logs", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public FileSystemResource showLog(@RequestParam("file") String logPath) throws UnsupportedEncodingException {
        File file = new File(URLDecoder.decode(logPath, "utf-8"));
        if (file.isFile() && file.canRead()) {
            return new FileSystemResource(file);
        } else {
            return null;
        }
    }
}
