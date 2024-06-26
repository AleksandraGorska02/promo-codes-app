package com.promoapp.promoapp.controller;

import com.promoapp.promoapp.db.entity.Code;
import com.promoapp.promoapp.db.entity.Product;
import com.promoapp.promoapp.db.entity.CalculatedResponse;
import com.promoapp.promoapp.db.entity.Purchase;
import com.promoapp.promoapp.db.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.promoapp.promoapp.db.service.CodeService;
import com.promoapp.promoapp.db.service.ProductService;

import java.lang.annotation.Target;

import static com.promoapp.promoapp.logic.Calculation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private final CodeService codeService;
    private final ProductService productService;
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(CodeService codeService, ProductService productService, PurchaseService purchaseService) {
        this.codeService = codeService;
        this.productService = productService;
        this.purchaseService = purchaseService;
    }


    @GetMapping("/discount/{productId}")
    public ResponseEntity<?> calculateDiscount(@PathVariable long productId, @RequestParam String code) {
        Code codeDetails = codeService.getCodeDetails(code);
        if (codeDetails == null) {
            return new ResponseEntity<>("Code not found", HttpStatus.NOT_FOUND);
        }
        Product productDetails = productService.getProductDetails(productId);
        if (productDetails == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(checkCodeValidity(codeDetails, productDetails), HttpStatus.OK);
    }

    @PostMapping("/buy/{productId}")
    public ResponseEntity<?> buyProduct(@PathVariable long productId, @RequestParam(required = false) String code) {

        Product productDetails = productService.getProductDetails(productId);
        if (productDetails == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        if (code == null) {

            Purchase purchase = purchaseService.savePurchaseWithoutCode(productDetails);
            return new ResponseEntity<>(purchase, HttpStatus.OK);
        }
        Code codeDetails = codeService.getCodeDetails(code);
        if (codeDetails == null) {
            return new ResponseEntity<>("Code not found", HttpStatus.NOT_FOUND);
        }

        CalculatedResponse calculateDiscount = checkCodeValidity(codeDetails, productDetails);

        if (!calculateDiscount.isValid()) {
            return new ResponseEntity<>(calculateDiscount, HttpStatus.OK);
        } else {
            Purchase purchase = purchaseService.savePurchaseWithCode(productDetails, codeDetails, calculateDiscount);
            return new ResponseEntity<>(purchase, HttpStatus.OK);

        }


    }


}
