package com.promoapp.promoapp.controller;

import com.promoapp.promoapp.db.entity.Product;
import com.promoapp.promoapp.db.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        if (product.getPrice() < 0) {
            return new ResponseEntity<>("Price must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if (productService.addProduct(product)) {
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("make sure you fill in all the fields", HttpStatus.BAD_REQUEST);


    }


    @PostMapping("edit/{id}")
    public ResponseEntity<?> editProduct(@PathVariable long id, @RequestBody Product product) {
        if(productService.getProductDetails(id) == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        if (product.getPrice() < 0) {
            return new ResponseEntity<>("Price must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        productService.editProduct(id, product);

        return new ResponseEntity<>("Product edited successfully", HttpStatus.OK);
    }
}
