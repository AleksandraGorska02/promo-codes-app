package com.promoapp.promoapp.controller;

import com.promoapp.promoapp.DB.Product.Product;
import com.promoapp.promoapp.DB.Product.ProductService;
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
        if(product.getPrice()<0){
            return new ResponseEntity<>("Price must be greater than 0",HttpStatus.BAD_REQUEST);
        }
        productService.addProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("edit/{name}")
    public ResponseEntity<?> editProduct(@PathVariable String name, @RequestBody Product product) {
        if(product.getPrice()<0){
            return new ResponseEntity<>("Price must be greater than 0",HttpStatus.BAD_REQUEST);
        }
        productService.editProduct(name, product);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
