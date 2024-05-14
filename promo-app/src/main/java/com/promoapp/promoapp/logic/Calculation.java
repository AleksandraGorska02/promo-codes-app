package com.promoapp.promoapp.logic;

import com.promoapp.promoapp.DB.Code.Code;
import com.promoapp.promoapp.DB.Product.Product;
import com.promoapp.promoapp.DB.Purchase.CalculateResponse;


import java.time.LocalDate;


public class Calculation {
    public static double calculateDiscountAmount(double price, double discount) {
        double discountPrice= price - discount;
        if(discountPrice < 0) {
            return 0;
        }
        return discountPrice;
    }
    public static double calculateDiscountPercentage(double price, double discount) {

        double discountAmount = price*discount/100;

        return price - discountAmount;

    }
    public static CalculateResponse checkCodeValidity(Code code, Product product) {
        String response = "";
        double price = product.getPrice();
        if(code.getExpirationDate().isBefore(LocalDate.now())) {
            response = "Code is expired";
           return new CalculateResponse(response, price,false, 0);
        }
        if(code.getCurrentUses()>=code.getMaxUses()) {
            response = "maximal number of usages was achieved";
            return new CalculateResponse(response, price,false, 0);
        }
        if(!code.getCurrency().contains(product.getCurrency())) {
            response = "Code currency does not match product currency";
            return new CalculateResponse(response, price,false, 0);
        }
        double discount = code.getDiscount();
        if(code.isPercentage()){
            price = calculateDiscountPercentage(price, discount);
            discount=price*discount/100;
        } else {
            price = calculateDiscountAmount(price, discount);
        }
        response = "Discount applied";
        return new CalculateResponse(response, price,true, discount);

    }
}
