package com.nextuple.pranay.backend.service;

import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;
import com.nextuple.pranay.backend.repo.InventoryRepo;
import com.nextuple.pranay.backend.repo.OrderRepo;
import com.nextuple.pranay.backend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    InventoryRepo inventoryRepo;

    @Autowired
    OrderRepo orderRepo;
    public Product createProduct(Product product){
        product.updateTimeStamp();
        productRepo.save(product);
        return product;
    }
    public Product findProductById(String productId){
        return productRepo.findById(productId).orElse(null);
    }
    public Product updateProductById(String productId, Product productChanges){
        Optional <Product> optionalProduct= productRepo.findById(productId);

        if(optionalProduct.isPresent()){
            Product presentProduct = optionalProduct.get();
            if(productChanges.getName()!=null){
                presentProduct.setName(productChanges.getName());
            }
            if(productChanges.getCategory()!=null){
                presentProduct.setCategory(productChanges.getCategory());
            }
            if(productChanges.getPrice() > 0){
                presentProduct.setPrice(productChanges.getPrice());
            }

            Product save = productRepo.save(presentProduct);
            System.out.println(save);
            return save;
        }else{
            return null;
        }
    }
    public boolean deleteProductById(String productId){
        Optional<Product> product = productRepo.findById(productId);
        if(product.isPresent()){
            productRepo.deleteById(productId);
            return true;
        }else{
            return false;
        }
    }

    public List<Product> listAllProducts(){
        return productRepo.findAll();
    }

    /*
     * Inventory Alone
     */

    public Inventory createInventory(Inventory inventory) {
        inventory.updateTimeStamp();
        inventoryRepo.save(inventory);
        return inventory;
    }

    public Inventory findInventoryById(String inventoryId) {
        return inventoryRepo.findById(inventoryId).orElse(null);
    }

    public List<Inventory> findInventoryByProductId(String productId) {
        return inventoryRepo.findAllByProductId(productId);
    }
    public Inventory updateInventoryById(String inventoryId, Inventory inventoryChanges) {
        Optional <Inventory> optionalInventory = inventoryRepo.findById(inventoryId);

        if(optionalInventory.isPresent()){
            Inventory presentInventory = optionalInventory.get();
            if(inventoryChanges.getQuantity()>0){
                presentInventory.setQuantity(inventoryChanges.getQuantity());
            }
            if(inventoryChanges.getSafeQuantity()>0){
                presentInventory.setSafeQuantity(inventoryChanges.getSafeQuantity());
            }

            Inventory save = inventoryRepo.save(presentInventory);
            System.out.println(save);
            return save;
        }else{
            return null;
        }
    }

    public boolean deleteInventoryById(String inventoryId) {
        Optional<Inventory> inventory = inventoryRepo.findById(inventoryId);
        if(inventory.isPresent()){
            inventoryRepo.deleteById(inventoryId);
            return true;
        }else{
            return false;
        }
    }

    public List<Inventory> listAllInventory() {
        return inventoryRepo.findAll();
    }


    /*
     * Order
     */

    public Order createSaleOrder(
            List<Order.ProductDetails> productCatalog
    ) {
        Order o1= new Order(productCatalog, Order.OrderType.SALE_ORDER);
        return orderRepo.save(o1);
    }

    public Order createPurchaseOrder(List<Order.ProductDetails> productCatalog) {
        Order o1= new Order(productCatalog, Order.OrderType.PURCHASE_ORDER);
        return orderRepo.save(o1);
    }

    public List<Order> listAllOrders() {
        return orderRepo.findAll();
    }

//    public List<Order> findOrderByProductId(String productId) {
//        return orderRepo.findOrdersByProductId(productId);
//    }
}
