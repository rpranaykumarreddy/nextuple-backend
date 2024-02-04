package com.nextuple.pranay.backend.controller.input;

import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductsCatalog {
    private List<ProductData> products= new ArrayList<>();
    public ProductsCatalog() {
        // Default constructor needed for Jackson deserialization
    }
    public static class ProductData{
        private String productId;
        private int quantity;
        private double price;
        private int safeQuantity;

        public ProductData(String productId, int quantity, double price, int safeQuantity) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
            this.safeQuantity = safeQuantity;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getSafeQuantity() {
            return safeQuantity;
        }
        public Order.ProductDetails toProductDetails(){
            return new Order.ProductDetails(productId, quantity, price);
        }

        @Override
        public String toString() {
            return  "productId=" + productId + "\t" +
                    "quantity=" + quantity + "\t" +
                    "price=" + price + "\t" +
                    "safeQuantity=" + safeQuantity + "\n";
        }
        public void setSafeQuantity(int safeQuantity) {
            this.safeQuantity = safeQuantity;
        }
    }

    public ProductsCatalog(List<ProductData> products) {
        this.products = products;
    }

    public List<ProductData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductData> products) {
        this.products = products;
    }

    public List<Order.ProductDetails> toProductDetails(){
        List<Order.ProductDetails> productDetails= new ArrayList<>();
        for(ProductData productData: products){
            productDetails.add(productData.toProductDetails());
        }
        return productDetails;
    }
    @Override
    public String toString() {
        return "ProductsCatalog:\n" +products + "\n";
    }
}