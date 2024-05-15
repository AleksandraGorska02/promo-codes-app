package com.promoapp.promoapp.db.service;

import com.promoapp.promoapp.db.entity.Product;
import com.promoapp.promoapp.db.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public boolean addProduct(Product product) {
        try {
            productRepository.save(product);
            return true;
        } catch (Exception ignored) {
            return false;
        }

    }

    public void editProduct(String name, Product product) {
        Product productToEdit = productRepository.findByName(name);
        if (product.getName() != null) {
            productToEdit.setName(product.getName());
        }
        if (product.getDescription() != null) {
            productToEdit.setDescription(product.getDescription());
        }
        if (product.getPrice() != 0) {
            productToEdit.setPrice(product.getPrice());
        }
        if (product.getCurrency() != null) {
            productToEdit.setCurrency(product.getCurrency());
        }

        productRepository.save(productToEdit);
    }

    public Product getProductDetails(String name) {
        return productRepository.findByName(name);
    }


}
