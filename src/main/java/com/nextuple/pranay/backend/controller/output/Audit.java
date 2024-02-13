package com.nextuple.pranay.backend.controller.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
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
        updateAuditTime();
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

    public void updateAuditTime() {
        this.auditTime = LocalDateTime.now();
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
    @Setter
    @Getter
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
        @Setter
        @Getter
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

    @Setter
    @Getter
    public static class Purchase{
        private long orderCount;
        private long uniqueProductCount;
        private long totalItemCount;
        private double totalExpenditure;
        private List<ProductPurchase> productDetails;

        private void sortProductDetails() {
            this.productDetails.sort((o1, o2) -> (int) (o2.getItemCount() - o1.getItemCount()));
        }

        private void updateUniqueProductCount() {
            this.uniqueProductCount = productDetails.size();
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
        @Setter
        @Getter
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
    @Getter
    @Setter
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

        @Setter
        @Getter
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
        }

    }
    /*
    Product without inventory
        -> ProductDetails (sort by productId ASC)
            -> productId
            -> productName
            -> productCategory
     */
    @Getter
    @Setter
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
            return Objects.hash(getCountOfProduct(), getProductDetails());
        }

        private void updateCountOfProduct() {
            this.countOfProduct = productDetails.size();
        }

        private void sortProductDetails() {
            this.productDetails.sort(Comparator.comparing(ProductDetails::getProductId));
        }
        @Setter
        @Getter
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
    @Getter
    @Setter
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
        @Setter
        @Getter
        public static class ProductDetails {
            private String productId;
            private String productName;
            private int currentStock;
            private int safeQuantity;
            private int stockNeeded;
            private double expenditureNeeded;
            public ProductDetails(String productId, String productName, int currentStock, int safeQuantity, int stockNeeded, double expenditureNeeded) {
                this.productId = productId;
                this.productName = productName;
                this.currentStock = currentStock;
                this.safeQuantity = safeQuantity;
                this.stockNeeded = stockNeeded;
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
        }
    }
}