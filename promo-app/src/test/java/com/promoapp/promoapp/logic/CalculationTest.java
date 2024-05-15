package com.promoapp.promoapp.logic;

import com.promoapp.promoapp.db.entity.CalculatedResponse;
import com.promoapp.promoapp.db.entity.Code;
import com.promoapp.promoapp.db.entity.Product;
import org.junit.jupiter.api.Test;



import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CalculationTest {



    @Test
    void calculateDiscountAmount() {
        assertEquals(0, Calculation.calculateDiscountAmount(0, 0));
        assertEquals(0, Calculation.calculateDiscountAmount(10, 10));
        assertEquals(5, Calculation.calculateDiscountAmount(10, 5));
        assertEquals(0, Calculation.calculateDiscountAmount(5, 10));
    }

    @Test
    void calculateDiscountPercentage() {
        assertEquals(0, Calculation.calculateDiscountPercentage(0, 0));
        assertEquals(0, Calculation.calculateDiscountPercentage(10, 100));
        assertEquals(5, Calculation.calculateDiscountPercentage(10, 50));
        assertEquals(0, Calculation.calculateDiscountPercentage(5, 100));
        assertEquals(90, Calculation.calculateDiscountPercentage(100, 10));
    }

    @Test
    void checkCodeValidityExpiredCode() {
        Code code = new Code();
        code.setExpirationDate(String.valueOf(LocalDate.now().minusDays(1)));  // Expired code
        code.setCurrentUses(0);
        code.setMaxUses(10);
        code.setCurrency("USD");
        code.setDiscount(10);
        code.setPercentage(true);

        Product product = new Product();
        product.setPrice(100);
        product.setCurrency("USD");

        CalculatedResponse response = Calculation.checkCodeValidity(code, product);
        assertEquals("Code is expired", response.getResponse());
        assertEquals(100, response.getPrice());
        assertFalse(response.isValid());
        assertEquals(0, response.getAmountOfDiscount());
    }
    @Test
    void checkCodeValidityMaxUsesReached() {
        Code code = new Code();
        code.setExpirationDate(String.valueOf(LocalDate.now().plusDays(1)));
        code.setCurrentUses(10);
        code.setMaxUses(10);
        code.setCurrency("USD");
        code.setDiscount(10);
        code.setPercentage(true);

        Product product = new Product();
        product.setPrice(100);
        product.setCurrency("USD");

        CalculatedResponse response = Calculation.checkCodeValidity(code, product);
        assertEquals("maximal number of usages was achieved", response.getResponse());
        assertEquals(100, response.getPrice());
        assertEquals(false, response.isValid());
        assertEquals(0, response.getAmountOfDiscount());
    }

    @Test
    void checkCodeValidityCurrencyMismatch() {
        Code code = new Code();
        code.setExpirationDate(String.valueOf(LocalDate.now().plusDays(1)));
        code.setCurrentUses(0);
        code.setMaxUses(10);
        code.setCurrency("EUR");
        code.setDiscount(10);
        code.setPercentage(true);

        Product product = new Product();
        product.setPrice(100);
        product.setCurrency("USD");

        CalculatedResponse response = Calculation.checkCodeValidity(code, product);
        assertEquals("Code currency does not match product currency", response.getResponse());
        assertEquals(100, response.getPrice());
        assertEquals(false, response.isValid());
        assertEquals(0, response.getAmountOfDiscount());
    }

    @Test
    void checkCodeValidityPercentageDiscount() {
        Code code = new Code();
        code.setExpirationDate(String.valueOf(LocalDate.now().plusDays(1)));
        code.setCurrentUses(0);
        code.setMaxUses(10);
        code.setCurrency("USD");
        code.setDiscount(10);
        code.setPercentage(true);

        Product product = new Product();
        product.setPrice(100);
        product.setCurrency("USD");

        CalculatedResponse response = Calculation.checkCodeValidity(code, product);
        assertEquals("Discount applied", response.getResponse());
        assertEquals(90, response.getPrice());
        assertEquals(true, response.isValid());
        assertEquals(10, response.getAmountOfDiscount());
    }

    @Test
    void checkCodeValidityAmountDiscount() {
        Code code = new Code();
        code.setExpirationDate(String.valueOf(LocalDate.now().plusDays(1)));
        code.setCurrentUses(0);
        code.setMaxUses(10);
        code.setCurrency("USD");
        code.setDiscount(10);
        code.setPercentage(false);

        Product product = new Product();
        product.setPrice(100);
        product.setCurrency("USD");

        CalculatedResponse response = Calculation.checkCodeValidity(code, product);
        assertEquals("Discount applied", response.getResponse());
        assertEquals(90, response.getPrice());
        assertEquals(true, response.isValid());
        assertEquals(10, response.getAmountOfDiscount());
    }

    @Test
    void checkCodeValidityDiscountBiggestThanPrice() {
        Code code = new Code();
        code.setExpirationDate(String.valueOf(LocalDate.now().plusDays(1)));
        code.setCurrentUses(0);
        code.setMaxUses(10);
        code.setCurrency("USD");
        code.setDiscount(110);
        code.setPercentage(false);

        Product product = new Product();
        product.setPrice(100);
        product.setCurrency("USD");

        CalculatedResponse response = Calculation.checkCodeValidity(code, product);
        assertEquals("Discount applied", response.getResponse());
        assertEquals(0, response.getPrice());
        assertEquals(true, response.isValid());
        assertEquals(100, response.getAmountOfDiscount());
    }

    @Test
    void checkCodeValidityManyCurrencies() {
        Code code = new Code();
        code.setExpirationDate(String.valueOf(LocalDate.now().plusDays(1)));
        code.setCurrentUses(0);
        code.setMaxUses(10);
        code.setCurrency("USD,EUR,PLN");
        code.setDiscount(10);
        code.setPercentage(false);

        Product product = new Product();
        product.setPrice(100);
        product.setCurrency("USD");

        CalculatedResponse response = Calculation.checkCodeValidity(code, product);
        assertEquals("Discount applied", response.getResponse());
        assertEquals(90, response.getPrice());
        assertEquals(true, response.isValid());
        assertEquals(10, response.getAmountOfDiscount());
    }



}