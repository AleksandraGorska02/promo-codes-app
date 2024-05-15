package com.promoapp.promoapp.db.entity;

public class CalculatedResponse {
    private String message;
    private double price;
    private boolean isValid;

    private double amountOfDiscount;


    public CalculatedResponse(String message, double price, boolean isValid, double amountOfDiscount) {
        this.message = message;
        this.price = price;
        this.isValid = isValid;
        this.amountOfDiscount = amountOfDiscount;
    }

    public String getResponse() {
        return message;
    }

    public double getPrice() {
        return price;
    }

    public boolean isValid() {
        return isValid;
    }

    public double getAmountOfDiscount() {
        return amountOfDiscount;
    }
}
