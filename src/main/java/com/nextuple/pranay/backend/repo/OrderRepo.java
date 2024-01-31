package com.nextuple.pranay.backend.repo;

import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepo extends MongoRepository<Order, String> {
//    @Query("{'productCatalog.productId': ?0}")
//    List<Order> findOrdersByProductId(String productId);
}