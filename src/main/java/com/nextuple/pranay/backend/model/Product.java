package com.nextuple.pranay.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Document(collection = "product")
public class Product {

    @Setter
    @Id
    private String id;
    private String name;
    private double price;
    private String category;

    @Setter
    private LocalDateTime lastTime;


    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        updateTimeStamp();
    }

    public void setName(String name) {
        this.name = name;
        updateTimeStamp();
    }

    public void setPrice(double price) {
        this.price = price;
        updateTimeStamp();
    }

    public void setCategory(String category) {
        this.category = category;
        updateTimeStamp();
    }

    public void updateTimeStamp() {
        this.lastTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", lastTime=" + lastTime +
                '}';
    }

}
