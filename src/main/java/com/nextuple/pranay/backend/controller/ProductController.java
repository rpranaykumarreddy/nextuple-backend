package com.nextuple.pranay.backend.controller;

import com.nextuple.pranay.backend.controller.validator.InputValidator;
import com.nextuple.pranay.backend.exception.CustomException;
import com.nextuple.pranay.backend.model.Product;
import com.nextuple.pranay.backend.service.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductServices productServices;
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        InputValidator.Products.createProduct(product);
        return productServices.createProduct(product);
    }
    @GetMapping("/{productId}")
    public Product findProductById(@PathVariable String productId){
        InputValidator.Products.findProductById(productId);
        return productServices.findProductById(productId);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProductById(@PathVariable String productId,@RequestBody Product productChanges){
        InputValidator.Products.updateProductById(productId, productChanges);
        return productServices.updateProductById(productId, productChanges);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable String productId){
        InputValidator.Products.deleteProductById(productId);
        return productServices.deleteProductById(productId);
    }
    @GetMapping("/list")
    public ResponseEntity<List<Product>> listAllProducts(){
        return productServices.listAllProducts();
    }
}
