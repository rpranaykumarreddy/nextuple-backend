package com.nextuple.pranay.backend.service;

import com.nextuple.pranay.backend.exception.CustomException;
import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.repo.InventoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServices {
    @Autowired
    InventoryRepo inventoryRepo;
    private static final Logger logger = LoggerFactory.getLogger(InventoryServices.class);

    public ResponseEntity<Inventory> createInventory(Inventory inventory) {
        try{
            inventory.updateTimeStamp();
            Inventory save = inventoryRepo.save(inventory);
            return new  ResponseEntity<>(save, HttpStatus.OK);
        }catch (Exception e){
            throw new CustomException.SaveNotSuccessfulException("Inventory Not Saved");
        }
    }

    public ResponseEntity<Inventory> findInventoryById(String inventoryId)  {
        Optional<Inventory> inventory = inventoryRepo.findById(inventoryId);
        if(inventory.isPresent()){
            return new ResponseEntity<>(inventory.get(), HttpStatus.OK);
        }else{
            throw new CustomException.InventoryNotFoundException("Inventory Not Found");
        }
    }

    public ResponseEntity<List<Inventory>> findInventoryByProductId(String productId)  {
        try{
            List<Inventory> allByProductId = inventoryRepo.findAllByProductId(productId);
            return new ResponseEntity<>(allByProductId, HttpStatus.OK);
        }catch (Exception e){
            throw new CustomException.InventoryNotFoundException("Inventory Not Found");
        }
    }
    public ResponseEntity<Inventory> updateInventoryById(String inventoryId, Inventory inventoryChanges)  {
        Optional<Inventory> optionalInventory = inventoryRepo.findById(inventoryId);
        if(optionalInventory.isPresent()) {
            Inventory presentInventory = optionalInventory.get();
            if (inventoryChanges.getQuantity() > 0) {
                presentInventory.setQuantity(inventoryChanges.getQuantity());
            }
            if (inventoryChanges.getSafeQuantity() > 0) {
                presentInventory.setSafeQuantity(inventoryChanges.getSafeQuantity());
            }
            try{
                Inventory save = inventoryRepo.save(presentInventory);
                return new ResponseEntity<>(save, HttpStatus.OK);
            }catch (Exception e){
                throw new CustomException.SaveNotSuccessfulException("Inventory Not Saved");
            }
        }else{
            throw new CustomException.InventoryNotFoundException("Inventory Not Found");
        }
    }

    public ResponseEntity<Boolean> deleteInventoryById(String inventoryId)  {
        Optional<Inventory> inventory = inventoryRepo.findById(inventoryId);
        if(inventory.isPresent()){
            try{
                inventoryRepo.deleteById(inventoryId);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }catch (Exception e){
                throw new CustomException.SaveNotSuccessfulException("Inventory Not Deleted");
            }
        }else{
            throw new CustomException.InventoryNotFoundException("Inventory Not Found");
        }
    }

    public ResponseEntity<List<Inventory>> listAllInventory() {
        try{
            List<Inventory> all = inventoryRepo.findAll();
            if(all.isEmpty()){
                throw new CustomException.InventoryNotFoundException("Inventory Not Found");
            }
            return new ResponseEntity<> (all, HttpStatus.OK);
        }catch (Exception e){
            throw new CustomException.InventoryNotFoundException("Inventory Not Found");
        }
    }

    public ResponseEntity<Boolean> updateInventories(List<Inventory> updatedInventories) {
        try {
            inventoryRepo.saveAll(updatedInventories);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException.SaveNotSuccessfulException("Inventory Not Saved");
        }
    }
}
