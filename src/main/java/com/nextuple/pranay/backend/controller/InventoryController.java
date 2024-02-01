package com.nextuple.pranay.backend.controller;

import com.nextuple.pranay.backend.exception.CustomException;
import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.service.InventoryServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    InventoryServices inventoryServices;
    private static final Logger logger = LoggerFactory.getLogger(InventoryServices.class);

    @PostMapping("/create")
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory){
        return inventoryServices.createInventory(inventory);
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<Inventory> findInventoryById(@PathVariable String inventoryId)  {
        return inventoryServices.findInventoryById(inventoryId);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Inventory>> findInventoryByProductId(@PathVariable String productId)  {
        return inventoryServices.findInventoryByProductId(productId);
    }
    @PutMapping("/{inventoryId}")
    public ResponseEntity<Inventory> updateInventoryById(@PathVariable String inventoryId, @RequestBody Inventory InventoryChanges)  {
        return inventoryServices.updateInventoryById(inventoryId, InventoryChanges);
    }
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Boolean> deleteInventoryById(@PathVariable String inventoryId)  {
        return inventoryServices.deleteInventoryById(inventoryId);
    }
    @GetMapping("/list")
    public ResponseEntity<List<Inventory>> listAllInventory()  {
        return inventoryServices.listAllInventory();
    }

}
