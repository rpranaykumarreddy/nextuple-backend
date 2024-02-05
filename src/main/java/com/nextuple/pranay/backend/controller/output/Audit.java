package com.nextuple.pranay.backend.controller.output;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audit audit = (Audit) o;
        return Objects.equals(sales, audit.sales) &&
                Objects.equals(purchase, audit.purchase) &&
                Objects.equals(profit, audit.profit) &&
                Objects.equals(productWithoutInventory, audit.productWithoutInventory) &&
                Objects.equals(productWithStockLessThanSafeQuantity, audit.productWithStockLessThanSafeQuantity);
    }
    @Override
    public int hashCode() {
        return Objects.hash(sales, purchase, profit, productWithoutInventory, productWithStockLessThanSafeQuantity);
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
                -> productDetails (sort by itemCount DSC)
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
            this.totalItemCount = totalItemCount;
            this.totalIncome = totalIncome;
            this.productDetails = productDetails;
            updateUniqueProductCount();
            sortProductDetails();
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sales sales = (Sales) o;
            return orderCount == sales.orderCount &&
                    uniqueProductCount == sales.uniqueProductCount &&
                    totalItemCount == sales.totalItemCount &&
                    Double.compare(sales.totalIncome, totalIncome) == 0 &&
                    Objects.equals(productDetails, sales.productDetails);
        }
        @Override
        public int hashCode() {
            return Objects.hash(orderCount, uniqueProductCount, totalItemCount, totalIncome, productDetails);
        }
        public void updateUniqueProductCount(){
            this.uniqueProductCount = productDetails.size();
        }
        public void sortProductDetails(){
            this.productDetails.sort((o1, o2) -> (int) (o2.getItemCount() - o1.getItemCount()));
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
            updateUniqueProductCount();
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
            updateUniqueProductCount();
            sortProductDetails();
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
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ProductSales that = (ProductSales) o;
                return itemCount == that.itemCount &&
                        Double.compare(that.totalIncome, totalIncome) == 0 &&
                        Double.compare(that.incomePerItem, incomePerItem) == 0 &&
                        Objects.equals(productId, that.productId) &&
                        Objects.equals(productName, that.productName);
            }
            @Override
            public int hashCode() {
                return Objects.hash(productId, productName, itemCount, totalIncome, incomePerItem);
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
        -> productDetails (sort by itemCount DSC)
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
            updateUniqueProductCount();
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
            updateUniqueProductCount();
            sortProductDetails();
        }

        public Purchase(long orderCount,  long totalItemCount, double totalExpenditure, List<ProductPurchase> productDetails) {
            this.orderCount = orderCount;
            this.totalItemCount = totalItemCount;
            this.totalExpenditure = totalExpenditure;
            this.productDetails = productDetails;
            updateUniqueProductCount();
            sortProductDetails();
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Purchase purchase = (Purchase) o;
            return orderCount == purchase.orderCount &&
                    uniqueProductCount == purchase.uniqueProductCount &&
                    totalItemCount == purchase.totalItemCount &&
                    Double.compare(purchase.totalExpenditure, totalExpenditure) == 0 &&
                    Objects.equals(productDetails, purchase.productDetails);
        }
        @Override
        public int hashCode() {
            return Objects.hash(orderCount, uniqueProductCount, totalItemCount, totalExpenditure, productDetails);
        }
        private void sortProductDetails() {
            this.productDetails.sort((o1, o2) -> (int) (o2.getItemCount() - o1.getItemCount()));
        }

        private void updateUniqueProductCount() {
            this.uniqueProductCount = productDetails.size();
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

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ProductPurchase that = (ProductPurchase) o;
                return itemCount == that.itemCount &&
                        Double.compare(that.totalExpenditure, totalExpenditure) == 0 &&
                        Double.compare(that.expenditurePerItem, expenditurePerItem) == 0 &&
                        Objects.equals(productId, that.productId) &&
                        Objects.equals(productName, that.productName);
            }

            @Override
            public int hashCode() {
                return Objects.hash(productId, productName, itemCount, totalExpenditure, expenditurePerItem);
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
        -> productDetails (sort by profit DSC)
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
            sortProductDetails();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Profit profit = (Profit) o;
            return Double.compare(profit.totalProfit, totalProfit) == 0 &&
                    Objects.equals(productDetails, profit.productDetails);
        }
        @Override
        public int hashCode() {
            return Objects.hash(totalProfit, productDetails);
        }

        private void sortProductDetails() {
            this.productDetails.sort((o1, o2) -> (int) (o2.getTotalProfit() - o1.getTotalProfit()));
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
            sortProductDetails();
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
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ProductProfit that = (ProductProfit) o;
                return Double.compare(that.totalProfit, totalProfit) == 0 &&
                        Double.compare(that.profitPerItem, profitPerItem) == 0 &&
                        Objects.equals(productId, that.productId) &&
                        Objects.equals(productName, that.productName);
            }
            @Override
            public int hashCode() {
                return Objects.hash(productId, productName, totalProfit, profitPerItem);
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
        -> ProductDetails (sort by productId ASC)
            -> productId
            -> productName
            -> productCategory
     */
    public static class ProductWithoutInventory {
        private long countOfProduct;
        private List<ProductDetails> productDetails;

        public ProductWithoutInventory(List<ProductDetails> productDetails) {
            this.productDetails = productDetails;
            sortProductDetails();
            updateCountOfProduct();
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductWithoutInventory that = (ProductWithoutInventory) o;
            return countOfProduct == that.countOfProduct &&
                    Objects.equals(productDetails, that.productDetails);
        }
        @Override
        public int hashCode() {
            return Objects.hash(countOfProduct, productDetails);
        }

        private void updateCountOfProduct() {
            this.countOfProduct = productDetails.size();
        }

        private void sortProductDetails() {
            this.productDetails.sort((o1, o2) -> o1.getProductId().compareTo(o2.getProductId()));
        }

        public long getCountOfProduct() {
            return countOfProduct;
        }

        public void setCountOfProduct(long countOfProduct) {
            updateCountOfProduct();
        }

        public List<ProductDetails> getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(List<ProductDetails> productDetails) {
            this.productDetails = productDetails;
            sortProductDetails();
            updateCountOfProduct();
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
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ProductDetails that = (ProductDetails) o;
                return Objects.equals(productId, that.productId) &&
                        Objects.equals(productName, that.productName) &&
                        Objects.equals(productCategory, that.productCategory);
            }
            @Override
            public int hashCode() {
                return Objects.hash(productId, productName, productCategory);
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
        -> ProductDetails (sort by expenditureNeeded DSC)
            -> productId
            -> productName
            -> currentStock
            -> safeQuantity
            -> stockNeeded
            -> expenditureNeeded
     */
    public static class ProductWithStockLessThanSafeQuantity {
        private long countOfProduct;
        private double totalExpenditureNeeded;
        private List<ProductDetails> productDetails;

        public ProductWithStockLessThanSafeQuantity( List<ProductDetails> productDetails) {
            this.productDetails = productDetails;
            sortProductDetails();
            updateCountOfProduct();
            updateTotalExpenditureNeeded();;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductWithStockLessThanSafeQuantity that = (ProductWithStockLessThanSafeQuantity) o;
            return countOfProduct == that.countOfProduct &&
                    Double.compare(that.totalExpenditureNeeded, totalExpenditureNeeded) == 0 &&
                    Objects.equals(productDetails, that.productDetails);
        }
        @Override
        public int hashCode() {
            return Objects.hash(countOfProduct, totalExpenditureNeeded, productDetails);
        }

        public void sortProductDetails() {
            this.productDetails.sort((o1, o2) -> (int) (o2.getExpenditureNeeded() - o1.getExpenditureNeeded()));
        }
        public void updateCountOfProduct() {
            this.countOfProduct = productDetails.size();
        }
        public void updateTotalExpenditureNeeded() {
            this.totalExpenditureNeeded = productDetails.stream().mapToDouble(ProductDetails::getExpenditureNeeded).sum();
        }

        public long getCountOfProduct() {
            return countOfProduct;
        }

        public void setCountOfProduct(long countOfProduct) {
            updateCountOfProduct();
        }

        public double getTotalExpenditureNeeded() {
            return totalExpenditureNeeded;
        }

        public void setTotalExpenditureNeeded(double totalExpenditureNeeded) {
            updateTotalExpenditureNeeded();
        }

        public List<ProductDetails> getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(List<ProductDetails> productDetails) {
            this.productDetails = productDetails;
            sortProductDetails();
            updateCountOfProduct();
            updateTotalExpenditureNeeded();
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
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ProductDetails that = (ProductDetails) o;
                return currentStock == that.currentStock &&
                        safeQuantity == that.safeQuantity &&
                        stockNeeded == that.stockNeeded &&
                        Double.compare(that.expenditureNeeded, expenditureNeeded) == 0 &&
                        Objects.equals(productId, that.productId) &&
                        Objects.equals(productName, that.productName);
            }
            @Override
            public int hashCode() {
                return Objects.hash(productId, productName, currentStock, safeQuantity, stockNeeded, expenditureNeeded);
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