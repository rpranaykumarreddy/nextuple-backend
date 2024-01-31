package com.nextuple.pranay.backend.service;

import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;
import com.nextuple.pranay.backend.repo.InventoryRepo;
import com.nextuple.pranay.backend.repo.OrderRepo;
import com.nextuple.pranay.backend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional
    public Order createSaleOrder(List<Order.ProductDetails> productCatalog) {
        double totalPrice=0.0;
        List<Inventory> updatedInventories = new ArrayList<>();
        for(Order.ProductDetails productDetails: productCatalog){
            Product product=findProductById(productDetails.getProductId());
            if(product!=null){
                int requestedQuantity = productDetails.getQuantity();
                int availableQuantity = getAvailableQuantityInInventory(product.getId());
                if(availableQuantity>=requestedQuantity){
                    List<Inventory> updatedInventory= deductFromInventory(product.getId(),requestedQuantity);
                    if(updatedInventory ==null) {
                        return null;
                    }
                    totalPrice += (productDetails.getPrice()>0
                            ? productDetails.getPrice():product.getPrice())* requestedQuantity;
                    updatedInventories.addAll(updatedInventory);
                } else{
                    return null;
                }
            }else{
                return null;
            }
        }
        inventoryRepo.saveAll(updatedInventories);
        Order order= new Order(productCatalog, Order.OrderType.SALE_ORDER, totalPrice);
        return orderRepo.save(order);
    }

    private List<Inventory> deductFromInventory(String productId, int requestedQuantity) {
        List<Inventory> inventories = inventoryRepo.findAllByProductId(productId);
        int remainingQuantity = requestedQuantity;
        List<Inventory> updatedInventories = new ArrayList<>();
        for(Inventory inventory: inventories){
            int availableQuantity = inventory.getQuantity();
            if(availableQuantity >= remainingQuantity){
                inventory.setQuantity(availableQuantity - remainingQuantity);
                updatedInventories.add(inventory);
                return updatedInventories;
            }else{
                remainingQuantity -= availableQuantity;
                inventory.setQuantity(0);
                updatedInventories.add(inventory);
            }
        }
        if(remainingQuantity>0){
            return null;
        }
        return updatedInventories;
    }

    private int getAvailableQuantityInInventory(String productId) {
    List<Inventory> inventories = inventoryRepo.findAllByProductId(productId);
    return inventories.stream().mapToInt(Inventory::getQuantity).sum();
    }
    @Transactional
    public Order createPurchaseOrder(List<Order.ProductDetails> productCatalog) {
        double totalPrice=0.0;
        List<Inventory> updatedInventories = new ArrayList<>();
        for(Order.ProductDetails productDetails: productCatalog){
            Product product=findProductById(productDetails.getProductId());
            if(product!=null){
                Inventory firstInventory = findFirstInventory(product.getId());
                if(firstInventory!=null){
                    firstInventory.setQuantity(firstInventory.getQuantity() + productDetails.getQuantity());
                    updatedInventories.add(firstInventory);
                }else{
                    Inventory newInventory = new Inventory(product.getId(), productDetails.getQuantity(),0);
                    updatedInventories.add(newInventory);
                }
                totalPrice += (productDetails.getPrice()>0
                        ? productDetails.getPrice():product.getPrice())* productDetails.getQuantity();
            }else{
                return null;
            }
        }
        inventoryRepo.saveAll(updatedInventories);
        Order order= new Order(productCatalog, Order.OrderType.PURCHASE_ORDER, totalPrice);
        return orderRepo.save(order);
    }

    private Inventory findFirstInventory(String productId) {
        List<Inventory> inventories = inventoryRepo.findAllByProductId(productId);
        return inventories.isEmpty() ? null : inventories.getFirst();
    }

    public List<Order> listAllOrders() {
        return orderRepo.findAll();
    }

    public List<Order> findOrderByProductId(String productId) {
        return orderRepo.findOrdersByProductId(productId);
    }

    public Order findOrderById(String orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    public boolean deleteOrderById(String orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if(order.isPresent()){
            orderRepo.deleteById(orderId);
            return true;
        }else{
            return false;
        }
    }
}
