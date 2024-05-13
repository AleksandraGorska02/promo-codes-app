package com.promoapp.promoapp.DB.Code;

import jakarta.persistence.*;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.time.LocalDate;


@Entity
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private LocalDate expirationDate;
    @Column(nullable = false, columnDefinition = "Decimal(10,2)")
    private double discount;
    @Column(nullable = false)
    private String currency;
    @Column(nullable = false)
    private boolean isPercentage;
    @Column(nullable = false)
    private int maxUses;
    @Column(nullable = false)
    private int currentUses=0;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {

        this.code = code;

    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = LocalDate.parse(expirationDate);

    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isPercentage() {
        return isPercentage;
    }

    public void setPercentage(boolean percentage) {
        isPercentage = percentage;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }

    public int getCurrentUses() {
        return currentUses;
    }

    public void setCurrentUses(int currentUses) {
        this.currentUses = currentUses;
    }
    public boolean checkCode(String code){
      String regex = "^[a-zA-Z0-9]{3,23}$";
if(!code.matches(regex)){
 return false;
      }


        return true;}
}
