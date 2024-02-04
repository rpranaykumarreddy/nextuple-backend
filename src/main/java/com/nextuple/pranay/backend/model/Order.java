package com.nextuple.pranay.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "order")
public class Order {
    @Id
    private String id;
    private OrderType type = OrderType.SALE_ORDER;
    private List<ProductDetails> productCatalog = new ArrayList<>();
    private double totalPrice;
    private LocalDateTime lastTime;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        updateTimeStamp();
    }

    public void setId(String order1Id) {
        this.id = order1Id;
    }

    public enum OrderType{SALE_ORDER, PURCHASE_ORDER}

    public Order(List<ProductDetails> productCatalog, OrderType type, double totalPrice) {
        this.type = type;
        this.productCatalog = productCatalog;
        this.totalPrice = totalPrice;
        updateTimeStamp();
    }

    public static class ProductDetails{
        private String productId;
        private int quantity;
        private double price;

        public ProductDetails(String productId, int quantity, double price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
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
        @Override
        public String toString() {
            return "ProductDetails{" +
                    "productId='" + productId + '\'' +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    '}';
        }
    }

    public String getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
        updateTimeStamp();
    }

    public List<ProductDetails> getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(List<ProductDetails> productCatalog) {
        this.productCatalog = productCatalog;
        updateTimeStamp();
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
    }

    public void updateTimeStamp() {
        this.lastTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", productCatalog=" + productCatalog +
                ", totalPrice=" + totalPrice +
                ", lastTime=" + lastTime +
                '}';
    }
}

