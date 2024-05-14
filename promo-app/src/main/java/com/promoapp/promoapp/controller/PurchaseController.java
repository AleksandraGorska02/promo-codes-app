package com.promoapp.promoapp.controller;

import com.promoapp.promoapp.DB.Code.Code;
import com.promoapp.promoapp.DB.Product.Product;
import com.promoapp.promoapp.DB.Purchase.CalculateResponse;
import com.promoapp.promoapp.DB.Purchase.Purchase;
import com.promoapp.promoapp.DB.Purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.promoapp.promoapp.DB.Code.CodeService;
import com.promoapp.promoapp.DB.Product.ProductService;

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


    @GetMapping("/discount/{product}")
    public ResponseEntity<?> calculateDiscount(@PathVariable String product, @RequestParam String code) {
        Code codeDetails = codeService.getCodeDetails(code);
        if (codeDetails == null) {
            return new ResponseEntity<>("Code not found", HttpStatus.NOT_FOUND);
        }
        Product productDetails = productService.getProductDetails(product);
        if (productDetails == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(checkCodeValidity(codeDetails, productDetails), HttpStatus.OK);
    }

    @PostMapping("/buy/{product}")
    public ResponseEntity<?> buyProduct(@PathVariable String product,  @RequestParam(required = false) String code) {
        if (code == null) {
            Product productDetails = productService.getProductDetails(product);
            if (productDetails == null) {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
            Purchase purchase = purchaseService.savePurchaseWithoutCode(productDetails);
            return new ResponseEntity<>(purchase, HttpStatus.OK);
        }
        Code codeDetails = codeService.getCodeDetails(code);
        if (codeDetails == null) {
            return new ResponseEntity<>("Code not found", HttpStatus.NOT_FOUND);
        }
        Product productDetails = productService.getProductDetails(product);
        if (productDetails == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        CalculateResponse calculateDiscount = checkCodeValidity(codeDetails, productDetails);

        if (!calculateDiscount.isValid()) {
            return new ResponseEntity<>(calculateDiscount, HttpStatus.OK);
        } else {
            Purchase purchase = purchaseService.savePurchaseWithCode(productDetails, codeDetails, calculateDiscount);
            return new ResponseEntity<>(purchase, HttpStatus.OK);

        }

    }

}
