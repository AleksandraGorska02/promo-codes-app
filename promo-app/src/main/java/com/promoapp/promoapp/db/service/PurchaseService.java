package com.promoapp.promoapp.db.service;

import com.promoapp.promoapp.db.entity.CalculatedResponse;
import com.promoapp.promoapp.db.entity.Code;
import com.promoapp.promoapp.db.entity.Purchase;
import com.promoapp.promoapp.db.repository.PurchaseRepository;
import com.promoapp.promoapp.db.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }


    public Purchase savePurchaseWithCode(Product productDetails, Code codeDetails, CalculatedResponse calculateResponse) {
        Purchase purchase = new Purchase();
        purchase.setProductName(productDetails.getName());
        purchase.setRegularPrice(productDetails.getPrice());
        purchase.setCurrency(productDetails.getCurrency());
        purchase.setAmountOfDiscount(calculateResponse.getAmountOfDiscount());
        purchase.setPurchaseDate(LocalDate.now());
        codeDetails.setCurrentUses(codeDetails.getCurrentUses() + 1);


        purchaseRepository.save(purchase);
        return purchase;
    }

    public Purchase savePurchaseWithoutCode(Product productDetails) {
        Purchase purchase = new Purchase();
        purchase.setProductName(productDetails.getName());
        purchase.setRegularPrice(productDetails.getPrice());
        purchase.setCurrency(productDetails.getCurrency());
        purchase.setAmountOfDiscount(0);
        purchase.setPurchaseDate(LocalDate.now());
        purchaseRepository.save(purchase);

        return purchase;
    }

    public List<Object[]> getSalesReport() {
        return purchaseRepository.findSalesReport();
    }


}
