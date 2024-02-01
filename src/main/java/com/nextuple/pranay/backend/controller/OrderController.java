package com.nextuple.pranay.backend.controller;

import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.service.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderServices orderServices;

    @PostMapping("/create-sale")
    public ResponseEntity<?> createSaleOrder(@RequestBody Order productCatalog){
        return orderServices.createSaleOrder(productCatalog.getProductCatalog());
    }
    @PostMapping("/create-purchase")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody Order productCatalog){
        return orderServices.createPurchaseOrder(productCatalog.getProductCatalog());
    }
    @GetMapping("/list")
    public ResponseEntity<List<Order>> listAllOrders(){
        return orderServices.listAllOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderById(@PathVariable String orderId){
        return orderServices.findOrderById(orderId);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Order>> findOrderByProductId(@PathVariable String productId){
        return orderServices.findOrderByProductId(productId);
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Boolean> deleteOrderById(@PathVariable String orderId){
        return orderServices.deleteOrderById(orderId);
    }


}
