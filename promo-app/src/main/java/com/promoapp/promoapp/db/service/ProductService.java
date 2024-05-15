package com.promoapp.promoapp.db.service;

import com.promoapp.promoapp.db.entity.Product;
import com.promoapp.promoapp.db.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    public void editProduct(long id, Product product) {
        Product productToEdit = productRepository.findById(id);

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

    public Product getProductDetails(long id) {
        return productRepository.findById(id);
    }


}
