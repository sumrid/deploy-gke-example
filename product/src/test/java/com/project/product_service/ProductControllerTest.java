package com.project.product_service;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.product_service.controller.ProductController;
import com.project.product_service.model.Product;
import com.project.product_service.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void postProductSuccess() throws Exception{

        Product test = new Product();
        test.setName("ProductName1");
        test.setDetail("PD detail1");
        test.setPrice(1000);
        test.setQuantity(10);
        test.setImg("PD1 Link img");

        when(productService.saveProduct(any(Product.class))).thenReturn(test);

        ResultActions result = mockMvc.perform(post("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(test)));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(test.getName())))
                .andExpect(jsonPath("$.price", is(test.getPrice())))
                .andExpect(jsonPath("$.quantity", is(test.getQuantity())))
                .andExpect(jsonPath("$.img", is(test.getImg())))
                .andExpect(jsonPath("$.detail", is(test.getDetail())));

        verify(productService).saveProduct(any(Product.class));
    }
}
