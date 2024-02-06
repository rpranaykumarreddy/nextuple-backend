package com.nextuple.pranay.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "inventory")
public class Inventory {
    @Setter
    @Id
    private String id;
    private String productId;
    private int quantity;
    private int safeQuantity;
    @Setter
    private LocalDateTime lastTime;

    public Inventory(String productId, int quantity, int safeQuantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.safeQuantity = safeQuantity;
        updateTimeStamp();
    }

    public void setProductId(String productId) {
        this.productId = productId;
        updateTimeStamp();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTimeStamp();
    }

    public void setSafeQuantity(int safeQuantity) {
        this.safeQuantity = safeQuantity;
        updateTimeStamp();
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
