package com.sumrid_k.pos.Report.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ProductQuantityTest {
    @Test
    public void testCreateObject() {
        ProductQuantity productQuantity = new ProductQuantity();
        productQuantity.setId(1);
        productQuantity.setName("lenovo");
        productQuantity.setPrice(456.2);
        productQuantity.setProductId(1L);
        productQuantity.setQuantity(1);

        assertEquals("lenovo", productQuantity.getName());
        assertEquals(1, productQuantity.getQuantity());
        assertEquals(1, productQuantity.getId());
        assertEquals(456.2, productQuantity.getPrice(), 0.01);
        assertEquals(1L, productQuantity.getProductId());

    }

    @Test
    public void testCreateOject() {
        ProductQuantity productQuantity = new ProductQuantity("lenovo", 1, 456,1L);

        assertEquals("lenovo", productQuantity.getName());
        assertEquals(1, productQuantity.getQuantity());
        assertEquals(456, productQuantity.getPrice(), 0.01);
        assertEquals(1L, productQuantity.getProductId());
    }

}