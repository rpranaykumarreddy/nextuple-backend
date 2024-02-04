package com.nextuple.pranay.backend.service;

import com.nextuple.pranay.backend.controller.output.Audit;
import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;
import com.nextuple.pranay.backend.repo.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuditServices {

    @Autowired
    OrderServices orderServices;
    @Autowired
    ProductServices productServices;
    @Autowired
    InventoryServices inventoryServices;
    private static List<Inventory> inventoriesList;
    private static List<Order> ordersList;
    private static List<Product> productsList;

    public ResponseEntity<Audit> audit() {
        inventoriesList = inventoryServices.listAllInventory().getBody();
        ordersList = orderServices.listAllOrders().getBody();
        productsList = productServices.listAllProducts().getBody();

        Audit.Sales salesAudit= createSalesAudit();
        Audit.Purchase purchaseAudit = createPurchaseAudit();
        Audit.Profit productProfit = createProductProfitAudit(salesAudit, purchaseAudit);
        Audit.ProductWithoutInventory productWithoutInventory = createProductWithoutInventoryAudit();
        Audit.ProductWithStockLessThanSafeQuantity productWithStockLessThanSafeQuantity = createProductWithStockLessThanSafeQuantityAudit();
        Audit audit = new Audit(salesAudit, purchaseAudit, productProfit, productWithoutInventory, productWithStockLessThanSafeQuantity);
        return new ResponseEntity<>(audit, HttpStatus.OK);
    }

    private Audit.ProductWithStockLessThanSafeQuantity createProductWithStockLessThanSafeQuantityAudit() {
        List<Audit.ProductWithStockLessThanSafeQuantity.ProductDetails> productWithStockLessThanSafeQuantityList = new ArrayList<>();
        for(Inventory inventory: inventoriesList){
            if(inventory.getQuantity() < inventory.getSafeQuantity()){
                Product product = productsList.stream().filter(product1 -> product1.getId().equals(inventory.getProductId())).findFirst().get();
                String productId = product.getId();
                String productName = product.getName();
                int currentStock = inventory.getQuantity();
                int safeQuantity = inventory.getSafeQuantity();
                int stockNeeded = safeQuantity - currentStock;
                double expenditureNeeded = stockNeeded * product.getPrice();
                productWithStockLessThanSafeQuantityList.add(new Audit.ProductWithStockLessThanSafeQuantity.ProductDetails(productId, productName, currentStock, safeQuantity, stockNeeded, expenditureNeeded));
            }
        }
        return new Audit.ProductWithStockLessThanSafeQuantity(productWithStockLessThanSafeQuantityList);
    }

    private Audit.ProductWithoutInventory createProductWithoutInventoryAudit() {
        List<Audit.ProductWithoutInventory.ProductDetails> productWithoutInventoryList = new ArrayList<>();
        for(Product product: productsList){
            Inventory inventory = inventoriesList.stream().filter(inventory1 -> inventory1.getProductId().equals(product.getId())).findFirst().orElse(null);
            if(inventory == null){
                productWithoutInventoryList.add(new Audit.ProductWithoutInventory.ProductDetails(product.getId(), product.getName(), product.getCategory()));
            }
        }
        return new Audit.ProductWithoutInventory(productWithoutInventoryList);
    }

    private Audit.Profit createProductProfitAudit(Audit.Sales salesAudit, Audit.Purchase purchaseAudit) {
        List<Audit.Profit.ProductProfit> productProfitDetailsList = new ArrayList<>();
        double totalProfit = 0.0;
        for(Audit.Sales.ProductSales productSales: salesAudit.getProductDetails()){
            String productId = productSales.getProductId();
            String productName = productSales.getProductName();
            long itemCount = productSales.getItemCount();
            double totalIncome = productSales.getTotalIncome();
            double incomePerItem = productSales.getIncomePerItem();
            Audit.Purchase.ProductPurchase productPurchase = purchaseAudit.getProductDetails().stream().filter(product -> product.getProductId().equals(productId)).findFirst().orElse(null);
            double profit = (incomePerItem - productPurchase.getExpenditurePerItem()) * itemCount;
            double profitPerItem = profit/itemCount;
            Audit.Profit.ProductProfit productProfit = new Audit.Profit.ProductProfit(productId, productName, profit, profitPerItem);
            productProfitDetailsList.add(productProfit);
            totalProfit += profit;
        }
        return new Audit.Profit(totalProfit,productProfitDetailsList);
    }


    private Audit.Purchase createPurchaseAudit() {
        List<Order> purchaseTypeOrdersList = ordersList.stream().filter(order -> order.getType().equals(Order.OrderType.PURCHASE_ORDER)).toList();
        List<Audit.Purchase.ProductPurchase> productPurchaseList = new ArrayList<>();
        long totalItemCount = 0;
        double totalExpense = 0.0;
        for(Order order: purchaseTypeOrdersList){
            for(Order.ProductDetails productDetails: order.getProductCatalog()){
                String productId = productDetails.getProductId();
                String name = productsList.stream().filter(product -> product.getId().equals(productId)).findFirst().get().getName();
                int quantity = productDetails.getQuantity();
                double price = productDetails.getPrice();
                Audit.Purchase.ProductPurchase productPurchase = productPurchaseList.stream().filter(product -> product.getProductId().equals(productId)).findFirst().orElse(null);;
                if(productPurchase == null){
                    productPurchase = new Audit.Purchase.ProductPurchase(productId, name, quantity, quantity*price, price);
                    productPurchaseList.add(productPurchase);
                }else{
                    productPurchase.setItemCount(productPurchase.getItemCount() + quantity);
                    productPurchase.setTotalExpenditure(productPurchase.getTotalExpenditure() + quantity*price);
                    productPurchase.setExpenditurePerItem(productPurchase.getTotalExpenditure()/productPurchase.getItemCount());
                }
                totalItemCount += productDetails.getQuantity();
                totalExpense += productDetails.getQuantity()*productDetails.getPrice();
            }
        }
        long orderCount = purchaseTypeOrdersList.size();
        return new Audit.Purchase(orderCount, totalItemCount, totalExpense, productPurchaseList);
    }

    private Audit.Sales createSalesAudit() {
        List<Order> salesTypeOrdersList = ordersList.stream().filter(order -> order.getType().equals(Order.OrderType.SALE_ORDER)).toList();
        List<Audit.Sales.ProductSales> productSalesList = new ArrayList<>();
        long totalItemCount = 0;
        double totalIncome = 0.0;
        for(Order order: salesTypeOrdersList){
            for(Order.ProductDetails productDetails: order.getProductCatalog()){
                String productId = productDetails.getProductId();
                String name = productsList.stream().filter(product -> product.getId().equals(productId)).findFirst().get().getName();
                int quantity = productDetails.getQuantity();
                double price = productDetails.getPrice();
                Audit.Sales.ProductSales productSales = productSalesList.stream().filter(product -> product.getProductId().equals(productId)).findFirst().orElse(null);;
                if(productSales == null){
                    productSales = new Audit.Sales.ProductSales(productId, name, quantity, quantity*price, price);
                    productSalesList.add(productSales);
                }else{
                    productSales.setItemCount(productSales.getItemCount() + quantity);
                    productSales.setTotalIncome(productSales.getTotalIncome() + quantity*price);
                    productSales.setIncomePerItem(productSales.getTotalIncome()/productSales.getItemCount());
                }
                totalItemCount += productDetails.getQuantity();
                totalIncome += productDetails.getQuantity()*productDetails.getPrice();
            }
        }
        long orderCount = salesTypeOrdersList.size();
        return new Audit.Sales(orderCount,  totalItemCount, totalIncome, productSalesList);
    }
}
