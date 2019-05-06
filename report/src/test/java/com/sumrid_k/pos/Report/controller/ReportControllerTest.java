package com.sumrid_k.pos.Report.controller;

import com.sumrid_k.pos.Report.service.ReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportControllerTest {

    @Mock
    private ReportService serviceMock;

    @Spy
    @InjectMocks
    private ReportController reportController;

    @Test
    public void index() {
        ModelAndView modelAndView = reportController.index();

        assertEquals("index.html", modelAndView.getViewName());
    }

    @Test
    public void testGetAllReports() {
        when(serviceMock.getReports()).thenReturn(new ArrayList<>());

        ResponseEntity responseEntity = reportController.getReport();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getData() {
    }

    @Test
    public void testFallbackGetAllData() {
        String errorMessage = "Request timeout, Please try again.";

        assertEquals(errorMessage, reportController.fallbackGetAllData());
    }

    @Test
    public void fallbackGetBill() {
        String errorMessage = "Request timeout, Please try again.";
        ResponseEntity responseEntity = reportController.fallbackGetBill();

        assertEquals(HttpStatus.REQUEST_TIMEOUT, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }
}