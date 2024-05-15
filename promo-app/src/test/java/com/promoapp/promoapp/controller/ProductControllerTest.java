package com.promoapp.promoapp.controller;

import com.promoapp.promoapp.db.entity.Product;
import com.promoapp.promoapp.db.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;


    //Get all products
    @Test
    void getAllProducts() throws Exception {

        Product product1 = new Product();
        product1.setName("product1");
        product1.setPrice(10);
        product1.setCurrency("USD");

        Product product2 = new Product();
        product2.setName("product2");
        product2.setPrice(20);
        product2.setCurrency("USD");

        when(productService.getAllProducts()).thenReturn(List.of(product1, product2));

        this.mockMvc.perform(get("/product/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"product1\",\"price\":10.0,\"currency\":\"USD\"},{\"name\":\"product2\",\"price\":20.0,\"currency\":\"USD\"}]"));
    }

    @Test
    void getAllProductsEmpty() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of());
        this.mockMvc.perform(get("/product/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    //Add product

    @Test
    void addProduct() throws Exception {

        when(productService.addProduct(any())).thenReturn(true);

        this.mockMvc.perform(post("/product/add")
                        .contentType("application/json")
                        .content("{\"name\":\"product1\",\"price\":10.0,\"currency\":\"USD\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added successfully"));

    }

    @Test
    void addNullProduct() throws Exception {

        when(productService.addProduct(any())).thenReturn(false);

        this.mockMvc.perform(post("/product/add")
                        .contentType("application/json")
                        .content("{\"name\":\"product1\",\"currency\":\"USD\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("make sure you fill in all the fields"));

    }

    @Test
    void addNegativePriceProduct() throws Exception {


        this.mockMvc.perform(post("/product/add")
                        .contentType("application/json")
                        .content("{\"name\":\"product1\",\"price\":-10.0,\"currency\":\"USD\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Price must be greater than 0"));

    }

    //Edit product

    @Test
    void editProduct() throws Exception {

        Product product1 = new Product();
        product1.setName("product1");
        product1.setPrice(10);
        product1.setCurrency("USD");

        when(productService.getProductDetails(1)).thenReturn(product1);
        when(productService.addProduct(any())).thenReturn(true);


        this.mockMvc.perform(post("/product/edit/1")
                        .contentType("application/json")
                        .content("{\"name\":\"product1\",\"price\":10.0,\"currency\":\"USD\"}"))
                .andExpect(content().string("Product edited successfully"));

    }

    @Test
    void editProductNotFound() throws Exception {


        this.mockMvc.perform(post("/product/edit/1")
                        .contentType("application/json")
                        .content("{\"name\":\"product1\",\"price\":10.0,\"currency\":\"USD\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));

    }

    @Test
    void editProductNegativePrice() throws Exception {

        Product product1 = new Product();
        product1.setName("product1");
        product1.setPrice(10);
        product1.setCurrency("USD");

        when(productService.getProductDetails(1)).thenReturn(product1);
        when(productService.addProduct(any())).thenReturn(true);

        this.mockMvc.perform(post("/product/edit/1")
                        .contentType("application/json")
                        .content("{\"name\":\"product1\",\"price\":-10.0,\"currency\":\"USD\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Price must be greater than 0"));
    }

}