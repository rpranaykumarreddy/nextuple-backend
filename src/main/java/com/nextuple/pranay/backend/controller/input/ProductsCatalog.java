package com.nextuple.pranay.backend.controller.input;

import com.nextuple.pranay.backend.model.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ProductsCatalog {
    private List<ProductData> products= new ArrayList<>();
    @Setter
    @Getter
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

        public Order.ProductDetails toProductDetails(){
            return new Order.ProductDetails(productId, quantity, price);
        }

    }

    public ProductsCatalog(List<ProductData> products) {
        this.products = products;
    }

}