package com.promoapp.promoapp.logic;

import com.promoapp.promoapp.db.entity.Code;
import com.promoapp.promoapp.db.entity.Product;
import com.promoapp.promoapp.db.entity.CalculatedResponse;


import java.math.BigDecimal;
import java.time.LocalDate;


public class Calculation {
    public static double calculateDiscountAmount(double price, double discount) {
        double discountPrice = price - discount;
        if (discountPrice < 0) {
            return 0;
        }
        return discountPrice;
    }

    public static double calculateDiscountPercentage(double price, double discount) {

        double discountAmount = price * (discount / 100);
        discountAmount=Math.round(discountAmount*100.0)/100.0;

        return price - discountAmount;




    }

    public static CalculatedResponse checkCodeValidity(Code code, Product product) {
        String response;
        double price = product.getPrice();
        if (code.getExpirationDate().isBefore(LocalDate.now())) {
            response = "Code is expired";
            return new CalculatedResponse(response, price, false, 0);
        }
        if (code.getCurrentUses() >= code.getMaxUses()) {
            response = "maximal number of usages was achieved";
            return new CalculatedResponse(response, price, false, 0);
        }
        if (!code.getCurrency().contains(product.getCurrency())) {
            response = "Code currency does not match product currency";
            return new CalculatedResponse(response, price, false, 0);
        }
       double discount = code.getDiscount();

        if (code.isPercentage()) {
            price = calculateDiscountPercentage(price, discount);
            discount = Math.round((product.getPrice() * (discount / 100)*100.0))/100.0;
        } else {
            price = calculateDiscountAmount(price, discount);
            if(price==0){
               discount= product.getPrice();
            }
        }
        response = "Discount applied";
        return new CalculatedResponse(response, price, true, discount);

    }

}
