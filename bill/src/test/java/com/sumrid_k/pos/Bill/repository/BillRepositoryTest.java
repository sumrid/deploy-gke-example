package com.sumrid_k.pos.Bill.repository;

import com.google.gson.Gson;
import com.sumrid_k.pos.Bill.model.Bill;
import com.sumrid_k.pos.Bill.model.Product;
import com.sumrid_k.pos.Bill.model.ProductQuantity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BillRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BillRepository repository;

    private Gson gson = new Gson();
    private Date date = new Date();

    @Before
    public void setUp() {
        Product dell = new Product();
        dell.setName("DELL ALIENWARE AW3418DW");
        dell.setDetail("Size : 34.14, IPS, 120Hz");
        dell.setPrice(36500);
        dell.setQuantity(50);
        dell.setImg("www.test.com/test.png");

        Product acer = new Product();
        acer.setName("ACER PREDATOR GALEA 300 PHW810");
        acer.setDetail("BLACK Features : Adjustable Headband , Retractable Microphone");
        acer.setPrice(3990);
        acer.setQuantity(150);
        acer.setImg("www.test.com/test.png");

        Product corsair = new Product();
        corsair.setName("CORSAIR HARPOON RGB");
        corsair.setDetail("Ideal for FPS gaming, the HARPOON RGB features a 6000 DPI optical gaming sensor with advanced tracking and high-speed motion detection for precise control.");
        corsair.setPrice(790);
        corsair.setQuantity(50);
        corsair.setImg("www.test.com/test.png");


        Bill bill1 = new Bill();
        bill1.setDate(date);
        bill1.setUserName("William Walker");
        bill1.setCompanyName("Butterscotch Corp");
        bill1.setTotalPrice(73000);

        Bill bill2 = new Bill();
        bill2.setDate(date);
        bill2.setUserName("Mylee Turnbull");
        bill2.setCompanyName("Grimm's Fairy Sales");
        bill2.setTotalPrice(3990);

        Bill bill3 = new Bill();
        bill3.setDate(date);
        bill3.setUserName("WAbdur Bowler");
        bill3.setCompanyName("Blind Decorate");
        bill3.setTotalPrice(37290);

        entityManager.persist(bill1);
        entityManager.persist(bill2);
        entityManager.persist(bill3);

    }

    @Test
    public void testFindAll() {
        ArrayList<Bill> bills = repository.findAll();

        assertEquals(3, bills.size());
    }

    @Test
    public void testFindAllByDate() {
        ArrayList<Bill> bills = repository.findAllByDate(new Date());
        Bill bill = bills.get(0);

        assertEquals(3, bills.size());
        assertEquals("William Walker", bill.getUserName());
        assertEquals("Butterscotch Corp", bill.getCompanyName());
        assertEquals(73000, bill.getTotalPrice(), 0.00);
        assertEquals(date, bill.getDate());
    }

    @Test
    public void testFindByUserName() {
        ArrayList<Bill> bills = repository.findAllByUserNameContains("William");
        Bill bill = bills.get(0);

        assertEquals(1, bills.size());
        assertEquals("William Walker", bill.getUserName());
    }

    @Test
    public void testFindByCompanyName() {
        ArrayList<Bill> bills = repository.findAllByCompanyNameContains("Blind Decorate");
        Bill bill = bills.get(0);

        assertEquals(1, bills.size());
        assertEquals("Blind Decorate", bill.getCompanyName());
    }
}