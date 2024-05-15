package com.promoapp.promoapp.controller;

import com.promoapp.promoapp.db.entity.Code;
import com.promoapp.promoapp.db.entity.Product;
import com.promoapp.promoapp.db.service.ProductService;
import com.promoapp.promoapp.db.service.PurchaseService;
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

import com.promoapp.promoapp.db.service.CodeService;
import com.promoapp.promoapp.db.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PurchaseService purchaseService;
    @MockBean
    private CodeService codeService;
    @MockBean
    private ProductService productService;

    //Calculate discount
    @Test
    void calculateDiscount() throws Exception {
        Code code = new Code();
        code.setCode("code");
        code.setDiscount(10);
       code.setMaxUses(10);
       code.setExpirationDate("2030-01-01");
       code.setCurrency("USD");
        when(codeService.getCodeDetails("code")).thenReturn(code);
        Product product = new Product();
        product.setName("product");
        product.setPrice(100);
        product.setCurrency("USD");
        when(productService.getProductDetails(1)).thenReturn(product);
        this.mockMvc.perform(get("/purchase/discount/1?code=code"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"price\": 90, \"amountOfDiscount\": 10, \"valid\": true, \"response\": \"Discount applied\"}"));
     }

    @Test
    void calculateDiscountCodeNotFound() throws Exception {
        when(codeService.getCodeDetails("code")).thenReturn(null);
        Product product = new Product();
        product.setName("product");
        product.setPrice(100);
        product.setCurrency("USD");
        when(productService.getProductDetails(1)).thenReturn(product);
        this.mockMvc.perform(get("/purchase/discount/1?code=code"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Code not found"));
    }

    @Test
    void calculateDiscountProductNotFound() throws Exception {
        Code code = new Code();
        code.setCode("code");
        code.setDiscount(10);
        code.setMaxUses(10);
        code.setExpirationDate("2030-01-01");
        code.setCurrency("USD");
        when(codeService.getCodeDetails("code")).thenReturn(code);
        when(productService.getProductDetails(1)).thenReturn(null);
        this.mockMvc.perform(get("/purchase/discount/1?code=code"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    @Test
    void calculateDiscountCodeNotValid() throws Exception {
        Code code = new Code();
        code.setCode("code");
        code.setDiscount(10);
        code.setMaxUses(0);
        code.setExpirationDate("2003-01-01");
        code.setCurrency("USD");
        when(codeService.getCodeDetails("code")).thenReturn(code);
        Product product = new Product();
        product.setName("product");
        product.setPrice(100);
        product.setCurrency("USD");
        when(productService.getProductDetails(1)).thenReturn(product);
        this.mockMvc.perform(get("/purchase/discount/1?code=code"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"price\": 100, \"amountOfDiscount\": 0, \"valid\": false, \"response\": \"Code is expired\"}"));
    }

    //Buy product


    @Test
    void buyProductNotFound() throws Exception {
        when(productService.getProductDetails(1)).thenReturn(null);
        this.mockMvc.perform(post("/purchase/buy/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }
    @Test
    void buyCodeNotFound() throws Exception {
        Product product = new Product();
        product.setName("product");
        product.setPrice(100);
        product.setCurrency("USD");
        when(productService.getProductDetails(1)).thenReturn(product);
        when(codeService.getCodeDetails("code")).thenReturn(null);
        this.mockMvc.perform(post("/purchase/buy/1?code=code"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Code not found"));
    }
    @Test
    void buyInvalidCode() throws Exception {
        Code code = new Code();
        code.setCode("code");
        code.setDiscount(10);
        code.setMaxUses(0);
        code.setExpirationDate("2003-01-01");
        code.setCurrency("USD");
        when(codeService.getCodeDetails("code")).thenReturn(code);
        Product product = new Product();
        product.setName("product");
        product.setPrice(100);
        product.setCurrency("USD");
        when(productService.getProductDetails(1)).thenReturn(product);
        this.mockMvc.perform(post("/purchase/buy/1?code=code"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"price\": 100, \"amountOfDiscount\": 0, \"valid\": false, \"response\": \"Code is expired\"}"));
    }


}
