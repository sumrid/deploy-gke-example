package com.sumrid_k.pos.Bill.controller;

import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sumrid_k.pos.Bill.model.Bill;
import com.sumrid_k.pos.Bill.model.Product;
import com.sumrid_k.pos.Bill.model.ProductQuantity;
import com.sumrid_k.pos.Bill.model.Stock;
import com.sumrid_k.pos.Bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private RestTemplate restTemplate;



    @GetMapping("/")
    public String index() {
        return "<h1 align=\"center\">Hello, this is bill service</h1>";
    }

    @GetMapping("/bills")
    @HystrixCommand(fallbackMethod = "fallbackBills")
    public ArrayList<Bill> getBills(){
        return billService.getAll();
    }

    @GetMapping("/bills/{id}")
    @HystrixCommand(fallbackMethod = "fallbackBill")
    public Bill getBill(@PathVariable long id){
        return billService.getBill(id);
    }

    @PostMapping("/bills")
    @HystrixCommand(fallbackMethod = "fallbackCreateBill",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})
    public ResponseEntity createBill(@RequestBody Bill request){
        restTemplate.postForEntity("http://report-service/bill/save", request, ResponseEntity.class);
        for(ProductQuantity q : request.getProductQuantities()){
            Stock stock = new Stock();
            stock.setDate(request.getDate());
            stock.setName(q.getName());
            stock.setPrice(q.getPrice());
            stock.setProductId(q.getProductId());
            stock.setQuantity(q.getQuantity()*(-1));
            restTemplate.postForObject("http://stock-service/stocks", stock, Stock.class);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(billService.saveBill(request));
    }

    @PutMapping("/bills/{id}")
    @HystrixCommand(fallbackMethod = "fallbackUpdateBill")
    public ResponseEntity updateBills(@PathVariable long id, @RequestBody Bill bill){
        if(!billService.updateBill(id, bill)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bill);
    }

    @DeleteMapping("/bills/{id}")
    @HystrixCommand(fallbackMethod = "fallbackDeleteBill")
    public ResponseEntity deleteBills(@PathVariable long id){
        if(!billService.deleteBill(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deleted fail");
        }
        return ResponseEntity.ok("Bill is deleted successfully");
    }

    @GetMapping("/bills/name/{name}")
    @HystrixCommand(fallbackMethod = "fallbackFindByName")
    public ArrayList<Bill> getByName(@PathVariable String name) {
        return billService.getByName(name);
    }

    @GetMapping("/bills/date/{date}")
    @HystrixCommand(fallbackMethod = "fallbackFindByName")
    public ArrayList<Bill> getByDate(@PathVariable String date) {
        return billService.getByDate(date);
    }

    @GetMapping("/bills/company/{name}")
    @HystrixCommand(fallbackMethod = "fallbackFindByName")
    public ArrayList<Bill> getByCompanyName(@PathVariable String name) {
        return billService.getByCompanyName(name);
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        Product p1 = new Product();
        p1.setDetail("for test product");
        p1.setName("Mock product 1");
        p1.setPrice(199.9);
        p1.setQuantity(50);
        p1.setImg("www.test.com/test.png");

        List<ProductQuantity> productQuantities = new ArrayList<>();
        // productQuantities.add(new ProductQuantity(gson.toJson(p1),1));

        Bill bill = new Bill();
        bill.setDate(new Date());
        bill.setProductQuantities(productQuantities);
        bill.setCompanyName("Apple Inc.");
        bill.setUserName("Jo Samon");
        billService.saveBill(bill);

        return ResponseEntity.status(HttpStatus.CREATED).body(bill);
    }

    public ArrayList<Bill> fallbackBills() {
        ArrayList<Bill> bills = new ArrayList<>();
        ArrayList<ProductQuantity> productQuantities = new ArrayList<>();
        Bill bill = new Bill();
        bill.setUserName("Request fails.");
        bill.setDate(new Date());
        bill.setCompanyName("Please try again.");
        bill.setTotalPrice(0);
        bill.setProductQuantities(productQuantities);
        bills.add(bill);
        return bills;
    }

    public Bill fallbackBill(long id) {
        Bill bill = new Bill();
        bill.setId(id);
        bill.setCompanyName("Request fails.");
        bill.setUserName("Please try again.");
        return bill;
    }

    public ArrayList<Bill> fallbackFindByName(String name) {
        Bill bill = new Bill();
        bill.setUserName(name);
        bill.setCompanyName("Request fails.");
        bill.setUserName("Please try again.");
        return new ArrayList<>(Arrays.asList(bill));
    }

    public ResponseEntity fallbackCreateBill(Bill bill) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request fails. Please try again.");
    }

    public ResponseEntity fallbackUpdateBill(long id, Bill bill) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(bill);
    }

    public ResponseEntity fallbackDeleteBill(long id) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout. Please try again.");
    }
}
