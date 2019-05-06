package com.sumrid_k.pos.Report.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class BillTest {
    @Test
    public void testCreateBill() {
        Bill bill = new Bill();
        bill.setId(1);
        Date date = new Date();
        bill.setDate(date);
        bill.setProductQuantities(new ArrayList<>());
        bill.setTotalPrice(5000);
        bill.setCompanyName("Thai Life Insurance");
        bill.setUserName("Naveed Blundell");

        assertEquals(1, bill.getId());
        assertEquals(date, bill.getDate());
        assertEquals(new ArrayList<>(), bill.getProductQuantities());
        assertEquals(5000, bill.getTotalPrice(), 0.00);
        assertEquals("Thai Life Insurance", bill.getCompanyName());
        assertEquals("Naveed Blundell", bill.getUserName());
    }
}