package com.nextuple.pranay.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Document(collection = "order")
public class Order {
    @Setter
    @Id
    private String id;
    private OrderType type;
    private List<ProductDetails> productCatalog;
    private double totalPrice;
    @Setter
    private LocalDateTime lastTime;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        updateTimeStamp();
    }

    public enum OrderType{SALE_ORDER, PURCHASE_ORDER}

    public Order(List<ProductDetails> productCatalog, OrderType type, double totalPrice) {
        this.type = type;
        this.productCatalog = productCatalog;
        this.totalPrice = totalPrice;
        updateTimeStamp();
    }

    @Setter
    @Getter
    public static class ProductDetails{
        private String productId;
        private int quantity;
        private double price;

        public ProductDetails(String productId, int quantity, double price) {
            this.productId = productId;
            this.quantity = quantity;
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

    public void setType(OrderType type) {
        this.type = type;
        updateTimeStamp();
    }

    public void setProductCatalog(List<ProductDetails> productCatalog) {
        this.productCatalog = productCatalog;
        updateTimeStamp();
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

