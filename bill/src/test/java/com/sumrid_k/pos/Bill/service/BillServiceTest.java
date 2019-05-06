package com.sumrid_k.pos.Bill.service;

import com.sumrid_k.pos.Bill.model.Bill;
import com.sumrid_k.pos.Bill.repository.BillRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BillServiceTest {

    @Mock
    private BillRepository billRepositoryMock;

    @Spy
    @InjectMocks
    private BillService billService;

    private Bill billMock;
    private ArrayList<Bill> billsListMock;

    @Before
    public void setUp() {
        billMock = new Bill();
        billMock.setDate(new Date());
        billMock.setTotalPrice(9500);
        billMock.setCompanyName("Apple Inc.");
        billMock.setUserName("Jo Samon");

        billsListMock = new ArrayList<>();
        billsListMock.add(billMock);
    }

    @Test
    public void testGetBillById() {
        when(billRepositoryMock.findById(anyLong())).thenReturn(billMock);

        Bill result = billService.getBill(1);

        assertEquals(billMock, result);
        assertEquals("Apple Inc.", result.getCompanyName());
    }

    @Test
    public void testGetAllBills() {
        when(billRepositoryMock.findAll()).thenReturn(billsListMock);

        ArrayList<Bill> result = billService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    public void testSaveBill() {
        when(billRepositoryMock.save(any(Bill.class))).thenReturn(billMock);

        Bill result = billService.saveBill(billMock);

        assertEquals(billMock, result);
    }

    @Test
    public void testDeleleBillSuccess() {
        when(billRepositoryMock.findById(anyLong())).thenReturn(billMock);
        doNothing().when(billRepositoryMock).delete(any(Bill.class));

        boolean result = billService.deleteBill(1);

        assertTrue(result);
    }

    @Test
    public void testDeleteBillFail() {
        when(billRepositoryMock.findById(anyLong())).thenReturn(null);

        boolean result = billService.deleteBill(1);

        assertFalse(result);
    }

    @Test
    public void testUpdateBillSuccess() {
        when(billRepositoryMock.findById(anyLong())).thenReturn(billMock);
        when(billRepositoryMock.save(any(Bill.class))).thenReturn(billMock);

        boolean result = billService.updateBill(1, billMock);

        assertTrue(result);
    }

    @Test
    public void testUpdateBillFail() {
        when(billRepositoryMock.findById(anyLong())).thenReturn(null);

        boolean result = billService.updateBill(1, billMock);

        assertFalse(result);
    }

    @Test
    public void testGetBillByUserName() {
        when(billRepositoryMock.findAllByCompanyNameContains(anyString())).thenReturn(billsListMock);

        ArrayList<Bill> result = billService.getByCompanyName("Jo");

        assertEquals(billsListMock, result);
        assertEquals("Jo Samon", result.get(0).getUserName());
    }

    @Test
    public void testGetBillByDateToday() {
        when(billRepositoryMock.findAllByDate(any(Date.class))).thenReturn(billsListMock);

        ArrayList<Bill> result = billService.getByDate("today");

        assertEquals(billsListMock, result);
    }

    @Test
    public void testGetBillByDateString() {
        String dateStr = "2019-02-3";
        when(billRepositoryMock.findAllByDate(any(Date.class))).thenReturn(billsListMock);

        ArrayList<Bill> result = billService.getByDate(dateStr);

        assertEquals(billsListMock, result);
    }

    @Test
    public void testGetByCompanyName() {
        when(billRepositoryMock.findAllByCompanyNameContains(anyString())).thenReturn(billsListMock);

        ArrayList<Bill> result = billService.getByCompanyName("Apple Inc.");

        assertEquals(1, result.size());
        assertEquals("Apple Inc.", result.get(0).getCompanyName());
    }
}