package com.sumrid_k.pos.Bill.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sumrid_k.pos.Bill.model.Bill;
import com.sumrid_k.pos.Bill.repository.BillRepository;
import com.sumrid_k.pos.Bill.service.BillService;
import net.bytebuddy.asm.Advice;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BillControllerTest {

    @Autowired
    private BillService billService;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private Gson gson;

    @Test
    public void getBills() {
        Bill bill = new Bill();
        bill.setId(1);
        bill.setDate(new Date());
        bill.setTotalPrice(99.99);
        bill.setCompanyName("company test Inc.");
        bill.setUserName("sumrid k");
        billService.saveBill(bill);

        // get response entity
        ResponseEntity respones = testRestTemplate.getForEntity("/bills", ArrayList.class);
        String json = gson.toJson(respones.getBody());
        System.out.println(json);

        // json to object
        Type type = new TypeToken<ArrayList<Bill>>(){}.getType();
        ArrayList<Bill> bills = gson.fromJson(json, type);
        Bill billRes = bills.get(0);

        assertEquals(1, billRes.getId());
        assertEquals(99.99, billRes.getTotalPrice(), 0.00);
        assertEquals("company test Inc.", billRes.getCompanyName());
        assertEquals("sumrid k", billRes.getUserName());
    }

    @Test
    public void getBill() {
        Bill bill = new Bill();
        bill.setId(1);
        bill.setDate(new Date());
        bill.setTotalPrice(200.99);
        bill.setCompanyName("company test 1");
        bill.setUserName("sumrid k");
        billService.saveBill(bill);

        Bill response = testRestTemplate.getForObject("/bills/1", Bill.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        assertEquals(1, response.getId());
        assertEquals(sdf.format(bill.getDate()), sdf.format(response.getDate()));
        assertEquals(200.99, response.getTotalPrice(), 0.00);
        assertEquals("company test 1", response.getCompanyName());
        assertEquals("sumrid k", response.getUserName());
    }

    @Test
    public void createBill() {
        Bill request = new Bill();
        request.setId(1);
        request.setDate(new Date());
        request.setTotalPrice(200.99);
        request.setCompanyName("company test 1");
        request.setUserName("sumrid k");

        Bill response = testRestTemplate.postForObject("/bills", request, Bill.class);

        assertEquals(1, response.getId());
        assertEquals(request.getDate(), response.getDate());
        assertEquals(200.99, response.getTotalPrice(), 0.00);
        assertEquals("company test 1", response.getCompanyName());
        assertEquals("sumrid k", response.getUserName());
    }

    @Test
    public void updateBills() {
    }

    @Test
    public void deleteBills() {
    }
}