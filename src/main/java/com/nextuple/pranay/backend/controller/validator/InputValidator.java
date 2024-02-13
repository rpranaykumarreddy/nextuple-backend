package com.nextuple.pranay.backend.controller.validator;

import com.nextuple.pranay.backend.controller.input.ProductsCatalog;
import com.nextuple.pranay.backend.exception.CustomException;
import com.nextuple.pranay.backend.model.Product;

public class InputValidator {
    public static void IdValidator(String id){
        if(id==null || id.isBlank()){
            throw new CustomException.ValidationException("Id cannot be empty");
        }
    }
    public static void nameValidator(String name){
        if(name==null || name.isBlank()){
            throw new CustomException.ValidationException("Name cannot be empty");
        }
        if(name.length()>50){
            throw new CustomException.ValidationException("Name cannot be more than 50 characters");
        }
        if(name.length()<3){
            throw new CustomException.ValidationException("Name cannot be less than 3 characters");
        }
    }
    public static void priceValidator(double price){
        if(price<0){
            throw new CustomException.ValidationException("Price cannot be less than or equal to 0");
        }
    }
    public static void categoryValidator(String category){
        if(category==null || category.isBlank() ){
            throw new CustomException.ValidationException("Category cannot be empty");
        }
        if(category.length()>20){
            throw new CustomException.ValidationException("Category cannot be more than 20 characters");
        }
        if(category.length()<3){
            throw new CustomException.ValidationException("Category cannot be less than 3 characters");
        }
    }
    public static void quantityValidator(int quantity){
        if(quantity<=0){
            throw new CustomException.ValidationException("Quantity cannot be less than or equal to 0");
        }
    }
    public static void safeQuantityValidator(int safeQuantity){
        if(safeQuantity<0){
            throw new CustomException.ValidationException("Safe Quantity cannot be less than or equal to 0");
        }
    }

    public static class Products{

        public static void createProduct(Product product) {
            nameValidator(product.getName());
            priceValidator(product.getPrice());
            categoryValidator(product.getCategory());
        }

        public static void findProductById(String productId) {
            IdValidator(productId);
        }

        public static void updateProductById(String productId, Product productChanges) {
            IdValidator(productId);
        }

        public static void deleteProductById(String productId) {
            IdValidator(productId);
        }
    }
    public static class Inventory{
        public static void createInventory(com.nextuple.pranay.backend.model.Inventory inventory) {
            IdValidator(inventory.getProductId());
            quantityValidator(inventory.getQuantity());
            safeQuantityValidator(inventory.getSafeQuantity());
        }

        public static void findInventoryById(String inventoryId) {
            IdValidator(inventoryId);
        }

        public static void findInventoryByProductId(String productId) {
            IdValidator(productId);
        }

        public static void updateInventoryById(String inventoryId, com.nextuple.pranay.backend.model.Inventory inventoryChanges) {
            IdValidator(inventoryId);
        }

        public static void deleteInventoryById(String inventoryId) {
            IdValidator(inventoryId);
        }

    }
    public static class Orders{
        public static void createSaleOrder(ProductsCatalog productCatalog) {
            for(ProductsCatalog.ProductData productData: productCatalog.getProducts()){
                IdValidator(productData.getProductId());
                quantityValidator(productData.getQuantity());
                priceValidator(productData.getPrice());
            }
        }

        public static void createPurchaseOrder(ProductsCatalog productCatalog) {
            for(ProductsCatalog.ProductData productData: productCatalog.getProducts()){
                IdValidator(productData.getProductId());
                quantityValidator(productData.getQuantity());
                priceValidator(productData.getPrice());
                safeQuantityValidator(productData.getSafeQuantity());
            }
        }

        public static void findOrderById(String orderId) {
            IdValidator(orderId);
        }

        public static void findOrderByProductId(String productId) {
            IdValidator(productId);
        }

        public static void deleteOrderById(String orderId) {
            IdValidator(orderId);
        }
    }
}
