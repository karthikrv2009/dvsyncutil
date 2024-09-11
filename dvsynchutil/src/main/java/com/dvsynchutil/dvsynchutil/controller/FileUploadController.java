
package com.dvsynchutil.dvsynchutil.controller;

import com.dvsynchutil.dvsynchutil.service.PollingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.ExecutionException;

@RestController
public class FileUploadController {

    @Autowired
    private PollingService pollingService;

    // Upload page to take model.json and log path
    @GetMapping("/")
    public ModelAndView uploadPage() {
        return new ModelAndView("upload");
    }

    // Endpoint to submit file paths
    @PostMapping("/upload")
    public String uploadPaths(@RequestParam("modelPath") String modelPath, @RequestParam("logPath") String logPath) {
        pollingService.setPaths(modelPath, logPath);
        return "Paths uploaded successfully!";
    }

    // For monitoring purposes, check the number of tables created and partitions processed
    @GetMapping("/status")
    public ModelAndView status() throws ExecutionException, InterruptedException {
        ModelAndView modelAndView = new ModelAndView("status");
        modelAndView.addObject("tables", pollingService.getTables());
        modelAndView.addObject("partitions", pollingService.getPartitionStatus());
        return modelAndView;
    }
}
