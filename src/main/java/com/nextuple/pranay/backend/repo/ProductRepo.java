package com.nextuple.pranay.backend.repo;

import com.nextuple.pranay.backend.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, String> {

}