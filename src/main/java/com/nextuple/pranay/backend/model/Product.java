package com.nextuple.pranay.backend.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "product")
public class Product {

    @Id
    private String id;
    private String name;
    private double price;
    private String category;

    private LocalDateTime lastTime;
    public void setId(String id) {
        this.id = id;
    }

    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
    }


    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        updateTimeStamp();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateTimeStamp();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        updateTimeStamp();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        updateTimeStamp();
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public void updateTimeStamp() {
        this.lastTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }
}
