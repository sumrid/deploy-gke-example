package com.project.product_service;


import com.project.product_service.model.Product;
import com.project.product_service.repository.ProductRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;


    private Product test;

    @Before
    public void setupData(){
        test = new Product();
        test.setName("ProductName1");
        test.setDetail("PD detail1");
        test.setPrice(1000);
        test.setQuantity(10);
        test.setImg("PD1 Link img");

        entityManager.persist(test);
    }

    @After
    public void clearData() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    public void findById() throws InterruptedException  {
        //when
        Optional<Product> result = productRepository.findById(test.getId());
        //then
        assertThat(result.get()).isEqualTo(test);
    }

    @Test
    public void findByName() throws InterruptedException  {
        //when
        Optional<Product> result = productRepository.findByName(test.getName());
        //then
        assertThat(result.get()).isEqualTo(test);
    }


}