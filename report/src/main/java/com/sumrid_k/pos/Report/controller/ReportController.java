package com.sumrid_k.pos.Report.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sumrid_k.pos.Report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;


    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("index.html");
        return view;
    }

    @GetMapping("/reports")
    @HystrixCommand(fallbackMethod = "fallbackGetBill")
    public ResponseEntity getReport() {
        return ResponseEntity.ok(reportService.getReports());
    }

    // FOR TEST | GET DATA FROM ALL SERVICES
    @GetMapping("/get-all-data")
    @HystrixCommand(fallbackMethod = "fallbackGetAllData",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "6000")})
    public String  getData() {
        reportService.getDataAllServices();
        return "Success";
    }

    public String fallbackGetAllData() {
        return "Request timeout, Please try again.";
    }

    public ResponseEntity fallbackGetBill() {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout, Please try again.");
    }
}
