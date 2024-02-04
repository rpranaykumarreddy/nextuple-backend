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

        List<Inventory> byProductId = inventoryRepo.findAllByProductId(inventory.getProductId());
        if(!byProductId.isEmpty()){
            throw new CustomException.InventoryAlreadyExistsException("Inventory Already Exists for Product ID: "+inventory.getProductId());
        }
        try{
            inventory.updateTimeStamp();
            Inventory save = inventoryRepo.save(inventory);
            return new  ResponseEntity<>(save, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException.SaveNotSuccessfulException("Inventory Not Created");
        }
    }

    public ResponseEntity<Inventory> findInventoryById(String inventoryId)  {
        Optional<Inventory> inventory = inventoryRepo.findById(inventoryId);
        if(inventory.isPresent()){
            return new ResponseEntity<>(inventory.get(), HttpStatus.OK);
        }else{
            throw new CustomException.InventoryNotFoundException("Inventory Not Found for ID: "+inventoryId);
        }
    }

    public ResponseEntity<Inventory> findInventoryByProductId(String productId)  {
        List<Inventory> allByProductId = inventoryRepo.findAllByProductId(productId);
        if(allByProductId.isEmpty()){
            throw new CustomException.InventoryNotFoundException("Inventory Not Found for Product ID: "+productId);
        }
        return new ResponseEntity<>(allByProductId.getFirst(), HttpStatus.OK);
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
                logger.error(e.getMessage());
                throw new CustomException.SaveNotSuccessfulException("Inventory Not Updated for ID: "+inventoryId);
            }
        }else{
            throw new CustomException.InventoryNotFoundException("Inventory Not Found for ID: "+inventoryId);
        }
    }

    public ResponseEntity<Boolean> deleteInventoryById(String inventoryId)  {
        Optional<Inventory> inventory = inventoryRepo.findById(inventoryId);
        if(inventory.isPresent()){
            try{
                inventoryRepo.deleteById(inventoryId);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }catch (Exception e){
                logger.error(e.getMessage());
                throw new CustomException.SaveNotSuccessfulException("Inventory Not Deleted for ID: "+inventoryId);
            }
        }else{
            throw new CustomException.InventoryNotFoundException("Inventory Not Found for ID: "+inventoryId);
        }
    }

    public ResponseEntity<List<Inventory>> listAllInventory() {
        List<Inventory> all = inventoryRepo.findAll();
        if(all.isEmpty()){
            throw new CustomException.InventoryNotFoundException("No Inventory Found");
        }
        return new ResponseEntity<> (all, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> updateInventories(List<Inventory> updatedInventories) {
        try {
            inventoryRepo.saveAll(updatedInventories);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new CustomException.SaveNotSuccessfulException("Unable to update Inventories for Order");
        }
    }
}
