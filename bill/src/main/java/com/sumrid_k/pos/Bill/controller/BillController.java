package com.sumrid_k.pos.Bill.controller;

import com.google.gson.Gson;
import com.sumrid_k.pos.Bill.model.Bill;
import com.sumrid_k.pos.Bill.model.Product;
import com.sumrid_k.pos.Bill.model.ProductQuantity;
import com.sumrid_k.pos.Bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private Gson gson;

    @GetMapping("/")
    public String index() {
        return "<h1>Hello, this is bill service</h1>";
    }

    @GetMapping("/bills")
    public ArrayList<Bill> getBills(){
        return billService.getAll();
    }

    @GetMapping("/bills/{id}")
    public Bill getBill(@PathVariable int id){
        return billService.getBill(id);
    }

    @PostMapping("/bills")
    public ResponseEntity createBill(@RequestBody Bill bill){
        billService.saveBill(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body(bill);
    }

    @PutMapping("/bills/{id}")
    public ResponseEntity updateBills(@PathVariable int id, @RequestBody Bill bill){
        HttpStatus status;
        if(billService.updateBill(id, bill)) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status(status).body(bill);
    }

    @DeleteMapping("/bills/{id}")
    public ResponseEntity deleteBills(@PathVariable int id){
        if(billService.deleteBill(id)) {
            return new ResponseEntity("Bill is deleted successfully",HttpStatus.OK);
        } else {
            return new ResponseEntity("Deleted fail", HttpStatus.NOT_FOUND);
        }
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
        productQuantities.add(new ProductQuantity(gson.toJson(p1),1));

        Bill bill = new Bill();
        bill.setDate(new Date());
        bill.setProductQuantities(productQuantities);
        bill.setCompanyName("Apple Inc.");
        bill.setUserName("Jo Samon");
        billService.saveBill(bill);

        return ResponseEntity.status(HttpStatus.CREATED).body(bill);
    }
}
