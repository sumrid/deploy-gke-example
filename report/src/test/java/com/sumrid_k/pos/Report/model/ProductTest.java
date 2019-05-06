package com.sumrid_k.pos.Report.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ProductTest {
    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setId(1l);
        product.setDetail("This is detail");
        product.setName("Name");
        product.setPrice(199.99);
        product.setQuantity(200);
        product.setImg("/product_1.png");

        assertEquals(1l, product.getId(), 0.00);
        assertEquals("This is detail", product.getDetail());
        assertEquals("Name", product.getName());
        assertEquals(199.99, product.getPrice(), 0.00);
        assertEquals(200, product.getQuantity());
        assertEquals("/product_1.png", product.getImg());
    }
}