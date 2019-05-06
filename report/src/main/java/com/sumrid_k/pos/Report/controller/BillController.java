package com.sumrid_k.pos.Report.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sumrid_k.pos.Report.model.Bill;
import com.sumrid_k.pos.Report.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/bill/save")
    @HystrixCommand(fallbackMethod = "fallbackSaveBill")
    public ResponseEntity saveBill(@RequestBody Bill bill) {
        Bill response = billService.save(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/bills")
    @HystrixCommand(fallbackMethod = "fallbackFidnAll")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(billService.findAll());
    }

    public ResponseEntity fallbackSaveBill(Bill bill) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(bill);
    }

    public ResponseEntity fallbackFidnAll() {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(new ArrayList<>());
    }
}
