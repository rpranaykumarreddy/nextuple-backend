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
    public Product createProduct(@RequestBody Product product){
        return service.createProduct(product);
    }
    @GetMapping("/product/{productId}")
    public Product FindProductById(@PathVariable String productId){
        return service.findProductById(productId);
    }
    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> UpdateProductById(@PathVariable String productId,@RequestBody Product productChanges){
        Product updatedProduct = service.updateProductById(productId, productChanges);
        if(updatedProduct !=null){
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/product/{productId}")
    public boolean DeleteProductById(@PathVariable String productId){
        return service.deleteProductById(productId);
    }
    @GetMapping("/product-list")
    public List<Product> ListAllProducts(){return service.listAllProducts();
    }

    /*
     * Inventory Alone
     */
    @PostMapping("/create-inventory")
    public Inventory CreateInventory(@RequestBody Inventory inventory){

        return service.createInventory(inventory);
    }

    @GetMapping("/inventory/{inventoryId}")
    public Inventory FindInventoryById(@PathVariable String inventoryId){
        return service.findInventoryById(inventoryId);
    }
    @GetMapping("/inventory/product/{productId}")
    public List<Inventory> FindInventoryByProductId(@PathVariable String productId){
        return service.findInventoryByProductId(productId);
    }
    @PutMapping("/inventory/{inventoryId}")
    public ResponseEntity<Inventory> UpdateInventoryById(@PathVariable String inventoryId,@RequestBody Inventory InventoryChanges){
        Inventory updatedInventory = service.updateInventoryById(inventoryId, InventoryChanges);
        if(updatedInventory !=null){
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/inventory/{inventoryId}")
    public boolean DeleteInventoryById(@PathVariable String inventoryId){
        return service.deleteInventoryById(inventoryId);
    }
    @GetMapping("/inventory-list")
    public List<Inventory> ListAllInventory(){
        return service.listAllInventory();
    }

    /*
     * Order
     */
    @PostMapping("/create-sale-order")
    public Order CreateSaleOrder(@RequestBody Order productCatalog){
        return service.createSaleOrder(productCatalog.getProductCatalog());
    }
    @PostMapping("/create-purchase-order")
    public Order CreatePurchaseOrder(@RequestBody Order productCatalog){
        return service.createPurchaseOrder(productCatalog.getProductCatalog());
    }
    @GetMapping("/order-list")
    public List<Order> ListAllOrders(){
        return service.listAllOrders();
    }

}
