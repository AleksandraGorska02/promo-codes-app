package com.promoapp.promoapp.db.dataSeeder;

import com.promoapp.promoapp.db.entity.Product;
import com.promoapp.promoapp.db.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductDataSeeder implements CommandLineRunner {
    private final ProductRepository productRepository;

    public ProductDataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private void seedData(ProductRepository productRepository) {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(10.99);
        product1.setCurrency("USD");

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(20.0);
        product2.setCurrency("USD");

        Product product3 = new Product();
        product3.setName("Product 3");
        product3.setDescription("Description 3");
        product3.setPrice(30.0);
        product3.setCurrency("EUR");

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }

    @Override
    public void run(String... args) throws Exception {
        seedData(productRepository);
    }
}
