package com.nextuple.pranay.backend.service;

import com.nextuple.pranay.backend.exception.CustomException;
import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;
import com.nextuple.pranay.backend.repo.InventoryRepo;
import com.nextuple.pranay.backend.repo.OrderRepo;
import com.nextuple.pranay.backend.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServices {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProductServices productServices;
    @Autowired
    InventoryServices inventoryServices;
    private static final Logger logger = LoggerFactory.getLogger(OrderServices.class);

    //FIXME: You should replace the repo with serivce
    @Transactional
    public ResponseEntity<Order> createSaleOrder(List<Order.ProductDetails> productCatalog) {
        double totalPrice=0.0;
        List<Inventory> updatedInventories = new ArrayList<>();
        for(Order.ProductDetails productDetails: productCatalog){
            Product product= productServices.findProductById(productDetails.getProductId());
            if(product!=null){
                int requestedQuantity = productDetails.getQuantity();
                int availableQuantity = getAvailableQuantityInInventory(product.getId());
                if(availableQuantity>=requestedQuantity){
                    List<Inventory> updatedInventory= deductFromInventory(product.getId(),requestedQuantity);
                    if(updatedInventory ==null) {
                        throw new CustomException.QuantityNotAvailableException("Product ID:" + productDetails.getProductId());
                    }
                    totalPrice += (productDetails.getPrice()>0
                            ? productDetails.getPrice() : product.getPrice())* requestedQuantity;
                    if(productDetails.getPrice()<=0)
                        productDetails.setPrice(product.getPrice());
                    updatedInventories.addAll(updatedInventory);
                } else{
                    throw new CustomException.QuantityNotAvailableException("Product ID:" + productDetails.getProductId());
                }
            }else{
                throw new CustomException.ProductNotFoundException("Product ID:" + productDetails.getProductId());
            }
        }
        inventoryServices.updateInventories(updatedInventories);
        try {
            Order order= new Order(productCatalog, Order.OrderType.SALE_ORDER, totalPrice);
            return new ResponseEntity<>(orderRepo.save(order), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException.SaveNotSuccessfulException("Order Not Saved");
        }
    }

    private List<Inventory> deductFromInventory(String productId, int requestedQuantity) {
        List<Inventory> inventories = inventoryServices.findInventoryByProductId(productId).getBody();
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
        List<Inventory> inventories = inventoryServices.findInventoryByProductId(productId).getBody();
        return inventories.stream().mapToInt(Inventory::getQuantity).sum();
    }
    @Transactional
    public ResponseEntity<Order> createPurchaseOrder(List<Order.ProductDetails> productCatalog) {
        double totalPrice=0.0;
        List<Inventory> updatedInventories = new ArrayList<>();
        for(Order.ProductDetails productDetails: productCatalog){
            Product product=productServices.findProductById(productDetails.getProductId());
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
                throw new CustomException.ProductNotFoundException("Product ID:" + productDetails.getProductId());
            }
        }
        inventoryServices.updateInventories(updatedInventories);
        try {
            Order order= new Order(productCatalog, Order.OrderType.PURCHASE_ORDER, totalPrice);
            return new ResponseEntity<>(orderRepo.save(order), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException.SaveNotSuccessfulException("Order Not Saved");
        }
    }

    private Inventory findFirstInventory(String productId) {
        List<Inventory> inventories = inventoryServices.findInventoryByProductId(productId).getBody();
        return inventories.isEmpty() ? null : inventories.getFirst();
    }

    public ResponseEntity<List<Order>> listAllOrders() {
        List<Order> all = orderRepo.findAll();
        if(all.isEmpty()){
            throw new CustomException.OrderNotFoundException("Order Not Found");
        }
        return new ResponseEntity<> (all, HttpStatus.OK);
    }

    public ResponseEntity<List<Order>> findOrderByProductId(String productId) {
        List<Order> all = orderRepo.findOrdersByProductId(productId);
        if(all.isEmpty()){
            throw new CustomException.OrderNotFoundException("Order Not Found for Product ID:" + productId);
        }
        return new ResponseEntity<> (all, HttpStatus.OK);
    }

    public ResponseEntity<Order> findOrderById(String orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        } else {
            throw new CustomException.OrderNotFoundException("Order ID:" + orderId);
        }
    }

    public ResponseEntity<Boolean> deleteOrderById(String orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if(order.isPresent()){
            try {
                orderRepo.deleteById(orderId);
            }catch (Exception e){
                logger.error(e.getMessage());
                throw new CustomException.SaveNotSuccessfulException("Order Not Deleted");
            }
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            throw new CustomException.OrderNotFoundException("Order ID:" + orderId);
        }
    }
}
