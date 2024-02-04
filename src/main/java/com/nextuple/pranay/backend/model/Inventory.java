package com.nextuple.pranay.backend.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Document(collection = "inventory")
public class Inventory {
    @Id
    private String id;
    private String productId;
    private int quantity;
    private int safeQuantity;
    private LocalDateTime lastTime;

    public Inventory(String productId, int quantity, int safeQuantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.safeQuantity = safeQuantity;
        updateTimeStamp();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {

        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
        updateTimeStamp();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTimeStamp();
    }

    public int getSafeQuantity() {
        return safeQuantity;
    }

    public void setSafeQuantity(int safeQuantity) {
        this.safeQuantity = safeQuantity;
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
        return "Inventory{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", safeQuantity=" + safeQuantity +
                ", lastTime=" + lastTime +
                '}';
    }

}
