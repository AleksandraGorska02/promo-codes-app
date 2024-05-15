package com.promoapp.promoapp.db.repository;

import com.promoapp.promoapp.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Product findByName(String name);
    Product findById(long id);

}
