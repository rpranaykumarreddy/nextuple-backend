package com.nextuple.pranay.backend.controller;

import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;
import com.nextuple.pranay.backend.service.AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    AllService service;
    /*
    * Product Alone
    */
    @PostMapping("/create-product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return service.createProduct(product);
    }
    @GetMapping("/product/{productId}")
    public Product findProductById(@PathVariable String productId){
        return service.findProductById(productId);
    }
    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProductById(@PathVariable String productId,@RequestBody Product productChanges){
        return service.updateProductById(productId, productChanges);
    }
    @DeleteMapping("/product/{productId}")
    public boolean deleteProductById(@PathVariable String productId){
        return service.deleteProductById(productId);
    }
    @GetMapping("/product-list")
    public List<Product> listAllProducts(){return service.listAllProducts();
    }

    /*
     * Inventory Alone
     */
    @PostMapping("/create-inventory")
    public Inventory createInventory(@RequestBody Inventory inventory){

        return service.createInventory(inventory);
    }

    @GetMapping("/inventory/{inventoryId}")
    public Inventory findInventoryById(@PathVariable String inventoryId){
        return service.findInventoryById(inventoryId);
    }
    @GetMapping("/inventory/product/{productId}")
    public List<Inventory> findInventoryByProductId(@PathVariable String productId){
        return service.findInventoryByProductId(productId);
    }
    @PutMapping("/inventory/{inventoryId}")
    public ResponseEntity<Inventory> updateInventoryById(@PathVariable String inventoryId,@RequestBody Inventory InventoryChanges){
        Inventory updatedInventory = service.updateInventoryById(inventoryId, InventoryChanges);
        if(updatedInventory !=null){
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/inventory/{inventoryId}")
    public boolean deleteInventoryById(@PathVariable String inventoryId){
        return service.deleteInventoryById(inventoryId);
    }
    @GetMapping("/inventory-list")
    public List<Inventory> listAllInventory(){
        return service.listAllInventory();
    }

    /*
     * Order
     */
    @PostMapping("/create-sale-order")
    public ResponseEntity<?> createSaleOrder(@RequestBody Order productCatalog){
        return service.createSaleOrder(productCatalog.getProductCatalog());
    }
    @PostMapping("/create-purchase-order")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody Order productCatalog){
        return service.createPurchaseOrder(productCatalog.getProductCatalog());
    }
    @GetMapping("/order-list")
    public List<Order> listAllOrders(){
        return service.listAllOrders();
    }

    @GetMapping("/order/{orderId}")
    public Order findOrderById(@PathVariable String orderId){
        return service.findOrderById(orderId);
    }
    @GetMapping("/order/product/{productId}")
    public List<Order> findOrderByProductId(@PathVariable String productId){
        return service.findOrderByProductId(productId);
    }

    @DeleteMapping("/order/{orderId}")
    public boolean deleteOrderById(@PathVariable String orderId){
        return service.deleteOrderById(orderId);
    }
}
