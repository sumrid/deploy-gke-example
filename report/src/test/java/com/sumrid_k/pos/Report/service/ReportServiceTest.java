package com.sumrid_k.pos.Report.service;

import com.google.gson.Gson;
import com.sumrid_k.pos.Report.model.Bill;
import com.sumrid_k.pos.Report.model.Product;
import com.sumrid_k.pos.Report.model.ProductQuantity;
import com.sumrid_k.pos.Report.model.Stock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;
    @Autowired
    private Gson gson;

    private ArrayList<Product> productList;
    private ArrayList<Stock> stockList;
    private ArrayList<Bill> billList;

    @Before
    public void setUp() throws ParseException {
        productList = new ArrayList<>();
        stockList = new ArrayList<>();
        billList = new ArrayList<>();

        Stock data1 = new Stock();
        data1.setDate((new SimpleDateFormat("yyyy-MM-dd")).parse("2019-05-04"));
        data1.setName("Apple Jumkad");
        data1.setPrice(25000.25);
        data1.setQuantity(4000);
        data1.setProductId(12907);

        Stock data2 = new Stock();
        data2.setDate((new SimpleDateFormat("yyyy-MM-dd")).parse("2019-05-04"));
        data2.setName("Android Jumkad");
        data2.setPrice(15000.75);
        data2.setQuantity(2000);
        data2.setProductId(12908);

        Stock data3 = new Stock();
        data3.setDate((new SimpleDateFormat("yyyy-MM-dd")).parse("2019-05-04"));
        data3.setName("XiaoMii Jumkad");
        data3.setPrice(12500.25);
        data3.setQuantity(1000);
        data3.setProductId(12909);

        Product p1 = new Product();
        p1.setName("DELL ALIENWARE AW3418DW 34.14' IPS 120Hz");
        p1.setDetail("Size : 34.14\"\n" +
                "Brightness : 300 cd/m²\n" +
                "Contrast : 1000 : 1\n" +
                "Response Time : 4 ms");
        p1.setPrice(36500);
        p1.setId(1L);
        p1.setQuantity(50);
        p1.setImg("www.test.com/test.png");

        Product p2 = new Product();
        p2.setName("ACER PREDATOR GALEA 300 PHW810");
        p2.setDetail("BLACK Features : Adjustable Headband , Retractable Microphone");
        p2.setPrice(3990);
        p2.setId(2L);
        p2.setQuantity(150);
        p2.setImg("www.test.com/test.png");

        Product p3 = new Product();
        p3.setName("CORSAIR HARPOON RGB");
        p3.setDetail("Ideal for FPS gaming, the HARPOON RGB features a 6000 DPI optical gaming sensor with advanced tracking and high-speed motion detection for precise control.");
        p3.setPrice(790);
        p3.setId(3L);
        p3.setQuantity(50);
        p3.setImg("www.test.com/test.png");

        List<ProductQuantity> productQuantities = new ArrayList<>();
        productQuantities.add(new ProductQuantity("DELL ALIENWARE AW3418DW 34.14' IPS 120Hz",1,36500,1));

        Bill bill1 = new Bill();
        bill1.setDate(new Date());
        bill1.setTotalPrice(99.99);
        bill1.setCompanyName("company test Inc.");
        bill1.setProductQuantities(productQuantities);
        bill1.setUserName("Ifan Davey");

        productQuantities.add(new ProductQuantity("ACER PREDATOR GALEA 300 PHW810",150,3990,2));

        Bill bill2 = new Bill();
        bill2.setDate(new Date());
        bill2.setTotalPrice(120);
        bill2.setCompanyName("Nokia Inc.");
        bill2.setProductQuantities(productQuantities);
        bill2.setUserName("Jame Oma");

        Bill bill3 = new Bill();
        bill3.setDate(new Date());
        bill3.setTotalPrice(2500.59);
        bill3.setCompanyName("TSK Trans");
        bill3.setProductQuantities(productQuantities);
        bill3.setUserName("sumrid k");

        stockList.add(data1);
        stockList.add(data2);
        stockList.add(data3);
        billList.add(bill1);
        billList.add(bill2);
        billList.add(bill3);
    }

    @Test
    public void testFindLowInventoryFromStock() throws ParseException {
        Stock result = reportService.findLowInventory(stockList);

        assertEquals(1000,result.getQuantity());
        assertEquals(12909, result.getProductId());
        assertEquals(12500.25, result.getPrice(), 0.00);
        assertEquals("XiaoMii Jumkad", result.getName());
        assertEquals((new SimpleDateFormat("yyyy-MM-dd")).parse("2019-05-04"), result.getDate());
    }

    // @Test
    public void testFindBestSellerProduct() {
        Product result = reportService.findBestSeller(billList);

        assertEquals(3990, result.getPrice(), 0.00);
        assertEquals("ACER PREDATOR GALEA 300 PHW810", result.getName());
    }

    @Test
    public void testCalculateDailyIncome() {
        double totalIncome = reportService.calculateIncome(billList);

        assertEquals(2720.58, totalIncome, 0.00);
    }

    @Test
    public void testCalculateDailyProfit() {
        double dailyProfit = reportService.calculateProfit();

        assertEquals(50000.5, dailyProfit, 0.00);
    }
}