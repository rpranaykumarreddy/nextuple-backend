package com.nextuple.pranay.backend.repo;

import com.nextuple.pranay.backend.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryRepo extends MongoRepository<Inventory, String> {
    List<Inventory> findAllByProductId(String productId);
}