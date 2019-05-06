package com.sumrid_k.pos.Report.controller;

import com.sumrid_k.pos.Report.model.Bill;
import com.sumrid_k.pos.Report.service.BillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BillControllerTest {

    @Spy
    @InjectMocks
    private BillController controller;

    @Mock
    private BillService serviceMock;
    private Bill billMock;

    @Before
    public void setUp() {
        billMock = new Bill();
        billMock.setId(1);
        billMock.setUserName("Viktoria Brooks");
        billMock.setCompanyName("Rushmere Court");
    }

    @Test
    public void saveBill() {
        when(serviceMock.save(any(Bill.class))).thenReturn(billMock);

        ResponseEntity responseEntity = controller.saveBill(billMock);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(billMock, responseEntity.getBody());
    }

    @Test
    public void findAll() {
        when(serviceMock.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity responseEntity = controller.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new ArrayList<>(), responseEntity.getBody());
    }

    @Test
    public void fallbackSaveBill() {
        ResponseEntity responseEntity = controller.fallbackSaveBill(billMock);

        assertEquals(HttpStatus.REQUEST_TIMEOUT, responseEntity.getStatusCode());
        assertEquals(billMock, responseEntity.getBody());
    }

    @Test
    public void fallbackFidnAll() {
        ResponseEntity responseEntity = controller.fallbackFidnAll();

        assertEquals(HttpStatus.REQUEST_TIMEOUT, responseEntity.getStatusCode());
    }
}