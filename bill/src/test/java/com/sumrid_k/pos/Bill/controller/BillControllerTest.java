package com.sumrid_k.pos.Bill.controller;

import com.sumrid_k.pos.Bill.model.Bill;
import com.sumrid_k.pos.Bill.model.ProductQuantity;
import com.sumrid_k.pos.Bill.service.BillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class BillControllerTest {

    @Mock
    private RestTemplate restTemplateMock;
    @Mock
    private BillService billServiceMock;

    @Spy
    @InjectMocks
    private BillController billController;

    private ArrayList<Bill> billListMock;
    private ArrayList<ProductQuantity> productQuantitiesMock;

    @Before
    public void setUp() {
        Bill bill = new Bill();
        bill.setId(1);
        bill.setDate(new Date());
        bill.setTotalPrice(99.99);
        bill.setCompanyName("Tie the Taught");
        bill.setUserName("sumrid k");

        Bill bil2 = new Bill();
        bil2.setId(2);
        bil2.setDate(new Date());
        bil2.setTotalPrice(200.99);
        bil2.setCompanyName("Barlow and Co");
        bil2.setUserName("Mylee Turnbull");

        billListMock = new ArrayList<>();
        billListMock.add(bill);
        billListMock.add(bil2);
    }

    @Test
    public void testIndex() {
        String response = billController.index();

        assertEquals("<h1 align=\"center\">Hello, this is bill service</h1>", response);
    }

    @Test
    public void testGetAllBillsSuccess() {
        when(billServiceMock.getAll()).thenReturn(billListMock);

        ArrayList<Bill> result = billController.getBills();

        assertEquals(2, result.size());
        verify(billServiceMock, atLeast(1)).getAll();
    }

    @Test
    public void testGetBillByIdSuccess() {
        when(billServiceMock.getBill(1)).thenReturn(billListMock.get(0));

        Bill result = billController.getBill(1);

        assertEquals("sumrid k", result.getUserName());
        assertEquals("Tie the Taught", result.getCompanyName());
        assertEquals(99.99, result.getTotalPrice(), 0.00);
    }

    @Test
    public void testCreateBillSuccess() {
        Bill request = new Bill();
        request.setId(3);
        request.setDate(new Date());
        request.setTotalPrice(200.99);
        request.setCompanyName("company Inc.");
        request.setUserName("Rora Back");
        request.setProductQuantities(productQuantitiesMock = new ArrayList<>());
        ResponseEntity responseEntityExpected = ResponseEntity.status(HttpStatus.CREATED).body(request);

        when(billServiceMock.saveBill(any(Bill.class))).thenReturn(request);
        when(restTemplateMock.postForEntity(anyString(), any(Bill.class), eq(ResponseEntity.class)))
                .thenReturn(responseEntityExpected);

        ResponseEntity responseEntity = billController.createBill(request);
        Bill response = (Bill) responseEntity.getBody();

        assertEquals("Rora Back", response.getUserName());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateBillSuccess() {
        Bill request = new Bill();
        request.setDate(new Date());
        request.setTotalPrice(1500);
        request.setCompanyName("724 Solutions Inc.");
        request.setUserName("Rio Tinto");

        when(billServiceMock.updateBill(anyLong(), any(Bill.class))).thenReturn(true);

        ResponseEntity responseEntity = billController.updateBills(1, request);
        Bill response = (Bill) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1500, response.getTotalPrice(), 0.00);
        assertEquals("Rio Tinto", response.getUserName());
    }

    @Test
    public void testUpdateBillFail() {
        when(billServiceMock.updateBill(anyLong(), any(Bill.class))).thenReturn(false);

        ResponseEntity responseEntity = billController.updateBills(1, new Bill());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteBillSuccess() {
        when(billServiceMock.deleteBill(anyLong())).thenReturn(true);

        ResponseEntity responseEntity = billController.deleteBills(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Bill is deleted successfully", responseEntity.getBody());

    }

    @Test
    public void testDeleteBillFail() {
        when(billServiceMock.deleteBill(anyLong())).thenReturn(false);

        ResponseEntity responseEntity = billController.deleteBills(1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Deleted fail", responseEntity.getBody());
    }

    @Test
    public void testGetBillByUserName() {
        ArrayList<Bill> request = new ArrayList<>();
        request.add(billListMock.get(0));

        when(billServiceMock.getByName(anyString())).thenReturn(request);

        ArrayList<Bill> response = billController.getByName("sumrid");

        assertEquals(1, response.size());
        assertEquals("sumrid k", response.get(0).getUserName());
    }

    @Test
    public void testGetByDate() {
        when(billServiceMock.getByDate(anyString())).thenReturn(billListMock);

        ArrayList<Bill> response = billController.getByDate("today");

        assertEquals(2, response.size());
    }

    @Test
    public void testGetByCompanyName() {
        ArrayList<Bill> request = new ArrayList<>();
        request.add(billListMock.get(1));

        when(billServiceMock.getByCompanyName(anyString())).thenReturn(request);

        ArrayList<Bill> response = billController.getByCompanyName("Barlow and Co");

        assertEquals("Barlow and Co", response.get(0).getCompanyName());
    }
}