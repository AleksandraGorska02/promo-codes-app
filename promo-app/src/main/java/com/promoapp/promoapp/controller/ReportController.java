package com.promoapp.promoapp.controller;

import com.promoapp.promoapp.DB.Purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class ReportController {
    private final PurchaseService purchaseService;

    @Autowired
    public ReportController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    @GetMapping("/report")
    public ResponseEntity<?> getSalesReport() {
        List<Object[]> reportData = purchaseService.getSalesReport();
        List<Map<String, Object>> response = reportData.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("currency", row[0]);
            map.put("totalAmount", row[1]);
            map.put("totalDiscount", row[2]);
            map.put("noOfPurchases", row[3]);
            return map;
        }).toList();
        return ResponseEntity.ok(response);
    }

}
