package com.nextuple.pranay.backend.service;

import com.nextuple.pranay.backend.controller.output.Audit;
import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;
import com.nextuple.pranay.backend.repo.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditServices {

    @Autowired
    OrderServices orderServices;
    @Autowired
    ProductServices productServices;
    @Autowired
    InventoryServices inventoryServices;
    private static final Logger logger = LoggerFactory.getLogger(OrderServices.class);

    public ResponseEntity<?> audit() {
        //TODO: Product sorted by sales
        //TODO: Product sorted by purchase
        //TODO: Product sorted by profit
        //TODO: Product without inventory
        //TODO: Product with stock less than safe quantity
        List<Inventory> inventories = inventoryServices.listAllInventory().getBody();
        List<Order> orders = orderServices.listAllOrders().getBody();
        List<Product> products = productServices.listAllProducts().getBody();



        return new ResponseEntity<>("send audit data", HttpStatus.OK);
    }
}
