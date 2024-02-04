package com.nextuple.pranay.backend.controller.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class Audit {
    private Sales sales;
    private Purchase purchase;
    private Profit profit;
    private ProductWithoutInventory productWithoutInventory;
    private ProductWithStockLessThanSafeQuantity productWithStockLessThanSafeQuantity;
    private LocalDateTime auditTime;

    public Audit(Sales sales, Purchase purchase, Profit profit, ProductWithoutInventory productWithoutInventory, ProductWithStockLessThanSafeQuantity productWithStockLessThanSafeQuantity) {
        this.sales = sales;
        this.purchase = purchase;
        this.profit = profit;
        this.productWithoutInventory = productWithoutInventory;
        this.productWithStockLessThanSafeQuantity = productWithStockLessThanSafeQuantity;
        this.auditTime = LocalDateTime.now();
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Profit getProfit() {
        return profit;
    }

    public void setProfit(Profit profit) {
        this.profit = profit;
    }

    public ProductWithoutInventory getProductWithoutInventory() {
        return productWithoutInventory;
    }

    public void setProductWithoutInventory(ProductWithoutInventory productWithoutInventory) {
        this.productWithoutInventory = productWithoutInventory;
    }

    public ProductWithStockLessThanSafeQuantity getProductWithStockLessThanSafeQuantity() {
        return productWithStockLessThanSafeQuantity;
    }

    public void setProductWithStockLessThanSafeQuantity(ProductWithStockLessThanSafeQuantity productWithStockLessThanSafeQuantity) {
        this.productWithStockLessThanSafeQuantity = productWithStockLessThanSafeQuantity;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

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

        public Sales(long orderCount,  long totalItemCount, double totalIncome, List<ProductSales> productDetails) {
            this.orderCount = orderCount;
            this.uniqueProductCount = productDetails.size();
            this.totalItemCount = totalItemCount;
            this.totalIncome = totalIncome;
            this.productDetails = productDetails;
        }

        public long getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(long orderCount) {
            this.orderCount = orderCount;
        }

        public long getUniqueProductCount() {
            return uniqueProductCount;
        }

        public void setUniqueProductCount(long uniqueProductCount) {
            this.uniqueProductCount = uniqueProductCount;
        }

        public long getTotalItemCount() {
            return totalItemCount;
        }

        public void setTotalItemCount(long totalItemCount) {
            this.totalItemCount = totalItemCount;
        }

        public double getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(double totalIncome) {
            this.totalIncome = totalIncome;
        }

        public List<ProductSales> getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(List<ProductSales> productDetails) {
            this.productDetails = productDetails;
        }

        public static class ProductSales{
            private String productId;
            private String productName;
            private long itemCount;
            private double totalIncome;
            private double incomePerItem;

            public ProductSales(String productId, String productName, long itemCount, double totalIncome, double incomePerItem) {
                this.productId = productId;
                this.productName = productName;
                this.itemCount = itemCount;
                this.totalIncome = totalIncome;
                this.incomePerItem = incomePerItem;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public long getItemCount() {
                return itemCount;
            }

            public void setItemCount(long itemCount) {
                this.itemCount = itemCount;
            }

            public double getTotalIncome() {
                return totalIncome;
            }

            public void setTotalIncome(double totalIncome) {
                this.totalIncome = totalIncome;
            }

            public double getIncomePerItem() {
                return incomePerItem;
            }

            public void setIncomePerItem(double incomePerItem) {
                this.incomePerItem = incomePerItem;
            }
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

        public long getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(long orderCount) {
            this.orderCount = orderCount;
        }

        public long getUniqueProductCount() {
            return uniqueProductCount;
        }

        public void setUniqueProductCount(long uniqueProductCount) {
            this.uniqueProductCount = uniqueProductCount;
        }

        public long getTotalItemCount() {
            return totalItemCount;
        }

        public void setTotalItemCount(long totalItemCount) {
            this.totalItemCount = totalItemCount;
        }

        public double getTotalExpenditure() {
            return totalExpenditure;
        }

        public void setTotalExpenditure(double totalExpenditure) {
            this.totalExpenditure = totalExpenditure;
        }

        public List<ProductPurchase> getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(List<ProductPurchase> productDetails) {
            this.productDetails = productDetails;
        }

        public Purchase(long orderCount,  long totalItemCount, double totalExpenditure, List<ProductPurchase> productDetails) {
            this.orderCount = orderCount;
            this.uniqueProductCount = productDetails.size();
            this.totalItemCount = totalItemCount;
            this.totalExpenditure = totalExpenditure;
            this.productDetails = productDetails;
        }

        public static class ProductPurchase{
            private String productId;
            private String productName;
            private long itemCount;
            private double totalExpenditure;
            private double expenditurePerItem;

            public ProductPurchase(String productId, String productName, long itemCount, double totalExpenditure, double expenditurePerItem) {
                this.productId = productId;
                this.productName = productName;
                this.itemCount = itemCount;
                this.totalExpenditure = totalExpenditure;
                this.expenditurePerItem = expenditurePerItem;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public long getItemCount() {
                return itemCount;
            }

            public void setItemCount(long itemCount) {
                this.itemCount = itemCount;
            }

            public double getTotalExpenditure() {
                return totalExpenditure;
            }

            public void setTotalExpenditure(double totalExpenditure) {
                this.totalExpenditure = totalExpenditure;
            }

            public double getExpenditurePerItem() {
                return expenditurePerItem;
            }

            public void setExpenditurePerItem(double expenditurePerItem) {
                this.expenditurePerItem = expenditurePerItem;
            }
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

        public Profit(double totalProfit, List<ProductProfit> productDetails) {
            this.totalProfit = totalProfit;
            this.productDetails = productDetails;
        }

        public double getTotalProfit() {
            return totalProfit;
        }

        public void setTotalProfit(double totalProfit) {
            this.totalProfit = totalProfit;
        }

        public List<ProductProfit> getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(List<ProductProfit> productDetails) {
            this.productDetails = productDetails;
        }

        public static class ProductProfit {
            private String productId;
            private String productName;
            private double totalProfit;
            private double profitPerItem;

            public ProductProfit(String productId, String productName, double totalProfit, double profitPerItem) {
                this.productId = productId;
                this.productName = productName;
                this.totalProfit = totalProfit;
                this.profitPerItem = profitPerItem;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public double getTotalProfit() {
                return totalProfit;
            }

            public void setTotalProfit(double totalProfit) {
                this.totalProfit = totalProfit;
            }

            public double getProfitPerItem() {
                return profitPerItem;
            }

            public void setProfitPerItem(double profitPerItem) {
                this.profitPerItem = profitPerItem;
            }


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
        private long countOfProduct;
        private List<ProductDetails> productDetails;

        public ProductWithoutInventory(List<ProductDetails> productDetails) {
            this.productDetails = productDetails;
            this.countOfProduct = productDetails.size();
        }

        public long getCountOfProduct() {
            return countOfProduct;
        }

        public void setCountOfProduct(long countOfProduct) {
            this.countOfProduct = countOfProduct;
        }

        public List<ProductDetails> getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(List<ProductDetails> productDetails) {
            this.productDetails = productDetails;
        }

        public static class ProductDetails {
            private String productId;
            private String productName;
            private String productCategory;

            public ProductDetails(String productId, String productName, String productCategory) {
                this.productId = productId;
                this.productName = productName;
                this.productCategory = productCategory;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductCategory() {
                return productCategory;
            }

            public void setProductCategory(String productCategory) {
                this.productCategory = productCategory;
            }
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

        public ProductWithStockLessThanSafeQuantity( List<ProductDetails> productDetails) {
            this.productDetails = productDetails;
            this.countOfProduct = productDetails.size();
            this.totalExpenditureNeeded = productDetails.stream().mapToDouble(ProductDetails::getExpenditureNeeded).sum();
        }

        public long getCountOfProduct() {
            return countOfProduct;
        }

        public void setCountOfProduct(long countOfProduct) {
            this.countOfProduct = countOfProduct;
        }

        public double getTotalExpenditureNeeded() {
            return totalExpenditureNeeded;
        }

        public void setTotalExpenditureNeeded(double totalExpenditureNeeded) {
            this.totalExpenditureNeeded = totalExpenditureNeeded;
        }

        public List<ProductDetails> getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(List<ProductDetails> productDetails) {
            this.productDetails = productDetails;
        }

        public static class ProductDetails {
            private String productId;
            private String productName;
            private int currentStock;
            private int safeQuantity;
            private int stockNeeded;
            private double expenditureNeeded;

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getCurrentStock() {
                return currentStock;
            }

            public void setCurrentStock(int currentStock) {
                this.currentStock = currentStock;
            }

            public int getSafeQuantity() {
                return safeQuantity;
            }

            public void setSafeQuantity(int safeQuantity) {
                this.safeQuantity = safeQuantity;
            }

            public int getStockNeeded() {
                return stockNeeded;
            }

            public void setStockNeeded(int stockNeeded) {
                this.stockNeeded = stockNeeded;
            }

            public double getExpenditureNeeded() {
                return expenditureNeeded;
            }

            public void setExpenditureNeeded(double expenditureNeeded) {
                this.expenditureNeeded = expenditureNeeded;
            }


            public ProductDetails(String productId, String productName, int currentStock, int safeQuantity, int stockNeeded, double expenditureNeeded) {
                this.productId = productId;
                this.productName = productName;
                this.currentStock = currentStock;
                this.safeQuantity = safeQuantity;
                this.stockNeeded = stockNeeded;
                this.expenditureNeeded = expenditureNeeded;
            }
        }
    }

}
