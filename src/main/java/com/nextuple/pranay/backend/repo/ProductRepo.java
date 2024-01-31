package com.nextuple.pranay.backend.repo;

import com.nextuple.pranay.backend.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.awt.*;
public interface ProductRepo extends MongoRepository<Product, String> {

}