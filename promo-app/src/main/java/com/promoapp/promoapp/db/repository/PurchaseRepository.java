package com.promoapp.promoapp.db.repository;

import com.promoapp.promoapp.db.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query("SELECT p.currency, SUM(p.regularPrice), SUM(p.amountOfDiscount), COUNT(p) " +
            "FROM Purchase p " +
            "GROUP BY p.currency")
    List<Object[]> findSalesReport();
}
