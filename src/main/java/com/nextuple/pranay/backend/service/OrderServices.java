package com.nextuple.pranay.backend.service;

import com.nextuple.pranay.backend.controller.input.ProductsCatalog;
import com.nextuple.pranay.backend.exception.CustomException;
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

    @Transactional
    public ResponseEntity<Order> createPurchaseOrder(ProductsCatalog productCatalog) {
        double totalPrice=0.0;
        List<Inventory> updatedInventories = new ArrayList<>();
        List <Order.ProductDetails> productDetailsList = new ArrayList<>();
        List<ProductsCatalog.ProductData> productCatalogProducts = productCatalog.getProducts();
        for(ProductsCatalog.ProductData productDetails: productCatalogProducts){
            Product product=productServices.findProductById(productDetails.getProductId());
            if(product!=null){
                if(productDetails.getQuantity()<=0){
                    throw new CustomException.QuantityIsZeroOrNegativeException("Product ID:" + productDetails.getProductId());
                }
                try{
                    Inventory firstInventory = inventoryServices.findInventoryByProductId(productDetails.getProductId()).getBody();
                    firstInventory.setQuantity(firstInventory.getQuantity() + productDetails.getQuantity());
                    if(productDetails.getSafeQuantity()>0)
                        firstInventory.setSafeQuantity(productDetails.getSafeQuantity());
                    updatedInventories.add(firstInventory);
                }catch (CustomException.InventoryNotFoundException e){
                    Inventory newInventory = new Inventory(product.getId(), productDetails.getQuantity(), productDetails.getSafeQuantity());
                    updatedInventories.add(newInventory);
                }
                totalPrice += (productDetails.getPrice()>0
                        ? productDetails.getPrice():product.getPrice())* productDetails.getQuantity();
            }else{
                throw new CustomException.ProductNotFoundException("Product ID:" + productDetails.getProductId());
            }
            productDetailsList.add(productDetails.toProductDetails());
        }
        if(productDetailsList.isEmpty()){
            throw new CustomException.ProductCatalogNotFoundExpception("Product Catalog is Empty");
        }
        inventoryServices.updateInventories(updatedInventories);
        try {
            Order order= new Order(productDetailsList, Order.OrderType.PURCHASE_ORDER, totalPrice);
            return new ResponseEntity<>(orderRepo.save(order), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException.SaveNotSuccessfulException("Order Not Saved for Purchase Order");
        }
    }

    @Transactional
    public ResponseEntity<Order> createSaleOrder(ProductsCatalog productCatalog) {
        double totalPrice=0.0;
        List<Inventory> updatedInventories = new ArrayList<>();
        List <Order.ProductDetails> productDetailsList = new ArrayList<>();
        List<ProductsCatalog.ProductData> productCatalogProducts = productCatalog.getProducts();
        for(ProductsCatalog.ProductData productDetails: productCatalogProducts){
            Product product= productServices.findProductById(productDetails.getProductId());
            if(product!=null){
                if(productDetails.getQuantity()<=0){
                    throw new CustomException.QuantityIsZeroOrNegativeException("Product ID:" + productDetails.getProductId());
                }
                Inventory firstInventory = inventoryServices.findInventoryByProductId(productDetails.getProductId()).getBody();
                if(firstInventory!=null){
                    if(firstInventory.getQuantity()>=productDetails.getQuantity()){
                        firstInventory.setQuantity(firstInventory.getQuantity() - productDetails.getQuantity());
                        updatedInventories.add(firstInventory);
                    }else{
                        throw new CustomException.QuantityNotAvailableException("Product ID:" + productDetails.getProductId());
                    }
                }else{
                    throw new CustomException.InventoryNotFoundException("Inventory Not Found for Product ID:" + productDetails.getProductId());
                }
                totalPrice += (productDetails.getPrice()>0
                        ? productDetails.getPrice() : product.getPrice())* productDetails.getQuantity();
                if(productDetails.getPrice()<=0)
                    productDetails.setPrice(product.getPrice());
            }else{
                throw new CustomException.ProductNotFoundException("Product ID:" + productDetails.getProductId());
            }
            productDetailsList.add(productDetails.toProductDetails());
        }
        if(productDetailsList.isEmpty()){
            throw new CustomException.ProductCatalogNotFoundExpception("Product Catalog is Empty");
        }
        inventoryServices.updateInventories(updatedInventories);
        try {
            Order order= new Order(productDetailsList, Order.OrderType.SALE_ORDER, totalPrice);
            return new ResponseEntity<>(orderRepo.save(order), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException.SaveNotSuccessfulException("Order Not Saved");
        }
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
