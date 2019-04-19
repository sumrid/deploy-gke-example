package com.sumrid_k.pos.Report.controller;

import com.sumrid_k.pos.Report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("index.html");
        return view;
    }

    @GetMapping("/reports")
    public ResponseEntity getReport() {
        return ResponseEntity.ok(reportService.getReports());
    }

    // for get data from all service
    // FOR TEST
    @GetMapping("/get-all-data")
    public void getData() {
        reportService.getDataAllServices();
    }

    @GetMapping("/testbill")
    public ResponseEntity getBill() {
        return restTemplate.getForEntity("http://bill-service/bills", Object.class);
    }
}
