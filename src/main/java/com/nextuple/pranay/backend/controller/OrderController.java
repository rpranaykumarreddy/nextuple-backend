package com.nextuple.pranay.backend.controller;

import com.nextuple.pranay.backend.controller.input.ProductsCatalog;
import com.nextuple.pranay.backend.controller.validator.InputValidator;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.service.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderServices orderServices;

    @PostMapping("/create-sale")
    public ResponseEntity<Order> createSaleOrder(@RequestBody ProductsCatalog productCatalog){
        InputValidator.Orders.createSaleOrder(productCatalog);
        return orderServices.createSaleOrder(productCatalog);
    }
    @PostMapping("/create-purchase")
    public ResponseEntity<Order> createPurchaseOrder(@RequestBody ProductsCatalog productCatalog){
        InputValidator.Orders.createPurchaseOrder(productCatalog);
        return orderServices.createPurchaseOrder(productCatalog);
    }
    @GetMapping("/list")
    public ResponseEntity<List<Order>> listAllOrders(){
        return orderServices.listAllOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderById(@PathVariable String orderId){
        InputValidator.Orders.findOrderById(orderId);
        return orderServices.findOrderById(orderId);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Order>> findOrderByProductId(@PathVariable String productId){
        InputValidator.Orders.findOrderByProductId(productId);
        return orderServices.findOrderByProductId(productId);
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Boolean> deleteOrderById(@PathVariable String orderId){
        InputValidator.Orders.deleteOrderById(orderId);
        return orderServices.deleteOrderById(orderId);
    }
}
