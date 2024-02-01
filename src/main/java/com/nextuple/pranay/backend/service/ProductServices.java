package com.nextuple.pranay.backend.service;

import com.nextuple.pranay.backend.exception.CustomException;
import com.nextuple.pranay.backend.model.Product;
import com.nextuple.pranay.backend.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {
    @Autowired
    ProductRepo productRepo;
    private static final Logger logger = LoggerFactory.getLogger(ProductServices.class);

    public ResponseEntity<Product> createProduct(Product product) {
        product.updateTimeStamp();
        try {
            productRepo.save(product);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException.SaveNotSuccessfulException("Product Not Saved");
        }
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    public Product findProductById(String productId){
        Optional<Product> product = productRepo.findById(productId);
        if(product.isPresent()) {
            return product.get();
        }else{
            throw new CustomException.ProductNotFoundException("Product ID:" + productId);
        }
    }
    public ResponseEntity<Product> updateProductById(String productId, Product productChanges){
        Optional<Product> optionalProduct= productRepo.findById(productId);

        if(optionalProduct.isPresent()){
            Product presentProduct = optionalProduct.get();
            if(productChanges.getName()!=null){
                presentProduct.setName(productChanges.getName());
            }
            if(productChanges.getCategory()!=null){
                presentProduct.setCategory(productChanges.getCategory());
            }
            if(productChanges.getPrice() > 0){
                presentProduct.setPrice(productChanges.getPrice());
            }
            Product save;
            try {
                save = productRepo.save(presentProduct);
            }catch (Exception e){
                logger.error(e.getMessage());
                throw new CustomException.SaveNotSuccessfulException("Product Not Saved");
            }
            return new ResponseEntity<>(save, HttpStatus.OK);
        }else{
            throw new CustomException.ProductNotFoundException("Product ID:" + productId);
        }
    }
    public ResponseEntity<Boolean> deleteProductById(String productId){
        Optional<Product> product = productRepo.findById(productId);
        if(product.isPresent()){
            try {
                productRepo.deleteById(productId);
            }catch (Exception e){
                logger.error(e.getMessage());
                throw new CustomException.SaveNotSuccessfulException("Product Not Deleted");
            }
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            throw new CustomException.ProductNotFoundException("Product ID:" + productId);
        }
    }

    public ResponseEntity<List<Product>> listAllProducts(){
        try {
            List<Product> allProducts = productRepo.findAll();
            return new ResponseEntity<>(allProducts, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException.ProductNotFoundException("Products Not Saved");
        }
    }

}
