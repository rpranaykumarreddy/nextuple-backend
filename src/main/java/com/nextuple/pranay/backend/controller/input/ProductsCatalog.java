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

        @Override
        public String toString() {
            return  "productId=" + productId + "\t" +
                    "quantity=" + quantity + "\t" +
                    "price=" + price + "\t" +
                    "safeQuantity=" + safeQuantity + "\n";
        }
    }

    public ProductsCatalog(List<ProductData> products) {
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