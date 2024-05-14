package com.promoapp.promoapp.DB.Purchase;

import com.promoapp.promoapp.DB.Code.Code;
import com.promoapp.promoapp.DB.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }


    public Purchase savePurchaseWithCode(Product productDetails, Code codeDetails, CalculateResponse calculateResponse) {
        Purchase purchase = new Purchase();
        purchase.setProductName(productDetails.getName());
        purchase.setRegularPrice(productDetails.getPrice());
        purchase.setCurrency(productDetails.getCurrency());
        purchase.setAmountOfDiscount(calculateResponse.getAmountOfDiscount());
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
        purchaseRepository.save(purchase);

        return purchase;
    }
    public List<Object[]> getSalesReport() {
        return purchaseRepository.findSalesReport();
    }


}
