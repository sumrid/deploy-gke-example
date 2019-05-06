package com.sumrid_k.pos.Report.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ReportTest {
    @Test
    public void testCreateReport() {
        Report report = new Report();
        report.setId(1);
        Date date = new Date();
        report.setDate(date);
        report.setBestseller("best seller json");
        report.setLowInventory("product json");
        report.setIncome(5000);
        report.setProfit(1200);

        assertEquals(1, report.getId());
        assertEquals(date, report.getDate());
        assertEquals("best seller json", report.getBestseller());
        assertEquals("product json", report.getLowInventory());
        assertEquals(5000, report.getIncome(), 0.00);
        assertEquals(1200, report.getProfit(), 0.00);
    }
}