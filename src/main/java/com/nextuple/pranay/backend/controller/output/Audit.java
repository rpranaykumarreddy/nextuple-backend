package com.nextuple.pranay.backend.controller.output;

import java.util.List;

public class Audit {
    private Sales sales;
    private Purchase purchase;
    private Profit profit;
    private ProductWithoutInventory productWithoutInventory;
    private ProductWithStockLessThanSafeQuantity productWithStockLessThanSafeQuantity;
    /*
    Sales
        -> orderCount
        -> uniqueProductCount
        -> totalItemCount
        -> totalIncome
        -> productDetails (sort by itemCount)
            -> productId
            -> productName
            -> itemCount
            -> totalIncome
            -> incomePerItem
     */
    public static class Sales{
        private long orderCount;
        private long uniqueProductCount;
        private long totalItemCount;
        private double totalIncome;
        private List<ProductSales> productDetails;
        public static class ProductSales{
            private String productId;
            private String productName;
            private long itemCount;
            private double totalIncome;
            private double incomePerItem;
        }

    }
    /*
    Purchase
        -> orderCount
        -> uniqueProductCount
        -> totalItemCount
        -> totalExpenditure
        -> productDetails (sort by itemCount)
            -> productId
            -> productName
            -> itemCount
            -> totalExpenditure
            -> expenditurePerItem
     */
    public static class Purchase{
        private long orderCount;
        private long uniqueProductCount;
        private long totalItemCount;
        private double totalExpenditure;
        private List<ProductPurchase> productDetails;
        public static class ProductPurchase{
            private String productId;
            private String productName;
            private long itemCount;
            private double totalExpenditure;
            private double expenditurePerItem;
        }
    }
    /*
    Profit
        -> totalProfit
        -> productDetails (sort by profit)
            -> productId
            -> productName
            -> totalProfit
            -> profitPerItem
            -> profitRank
     */
    public static class Profit {
        private double totalProfit;
        private List<ProductProfit> productDetails;

        public static class ProductProfit {
            private String productId;
            private String productName;
            private double totalProfit;
            private double profitPerItem;
            private long profitRank;
        }
    }
    /*
    Product without inventory
        -> ProductDetails
            -> productId
            -> productName
            -> productCategory
     */
    public static class ProductWithoutInventory {
        private List<ProductDetails> productDetails;

        public static class ProductDetails {
            private String productId;
            private String productName;
            private String productCategory;
        }
    }
    /*
    Products with stock less than safe quantity
        -> countOfProduct
        -> totalExpenditureNeeded
        -> ProductDetails
            -> productId
            -> productName
            -> currentStock
            -> safeQuantity
            -> stockNeeded
            -> expenditureNeeded
            -> profitRank
     */
    public static class ProductWithStockLessThanSafeQuantity {
        private long countOfProduct;
        private double totalExpenditureNeeded;
        private List<ProductDetails> productDetails;

        public static class ProductDetails {
            private String productId;
            private String productName;
            private int currentStock;
            private int safeQuantity;
            private int stockNeeded;
            private double expenditureNeeded;
            private long profitRank;
        }
    }
}
