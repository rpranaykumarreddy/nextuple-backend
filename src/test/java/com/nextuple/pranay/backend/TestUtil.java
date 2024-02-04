package com.nextuple.pranay.backend;

import com.nextuple.pranay.backend.controller.input.ProductsCatalog;
import com.nextuple.pranay.backend.controller.output.Audit;
import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TestUtil {
    public static class ProductTestData {
        public static final String PRODUCT1_ID = "1";
        public static final String PRODUCT2_ID = "2";
        public static final String PRODUCT1_NAME = "Milk Packet";
        public static final String PRODUCT2_NAME = "Salt Bag";

        public static final double PRODUCT1_PRICE = 10.0;
        public static final double PRODUCT2_PRICE = 5.0;
        public static final double PRODUCT1_NEW_PRICE = 12.0;
        public static final String PRODUCT1_CATEGORY = "Dairy";
        public static final String PRODUCT2_CATEGORY = "Grocery";
        public static final int PRODUCT_LIST_SIZE = 2;

        public static Product getProduct1Request() {
            return new Product(PRODUCT1_NAME, PRODUCT1_PRICE, PRODUCT1_CATEGORY);
        }

        public static Product getProduct2Request() {
            return new Product(PRODUCT2_NAME, PRODUCT2_PRICE, PRODUCT2_CATEGORY);
        }

        public static Product getProduct1Response() {
            Product product = new Product(PRODUCT1_NAME, PRODUCT1_PRICE, PRODUCT1_CATEGORY);
            product.setId(PRODUCT1_ID);
            return product;
        }

        public static Product getProduct2Response() {
            Product product = new Product(PRODUCT2_NAME, PRODUCT2_PRICE, PRODUCT2_CATEGORY);
            product.setId(PRODUCT2_ID);
            return product;
        }

        public static Product getProduct1RequestIncreasePrice() {
            return new Product(PRODUCT1_NAME, PRODUCT1_NEW_PRICE, PRODUCT1_CATEGORY);
        }

        public static Product getProduct1ResponseIncreasePrice() {
            Product product = new Product(PRODUCT1_NAME, PRODUCT1_NEW_PRICE, PRODUCT1_CATEGORY);
            product.setId(PRODUCT1_ID);
            return product;
        }

        public static List<Product> getProductList() {
            return Arrays.asList(getProduct1Response(), getProduct2Response());
        }

        public static List<Product> getEmptyProductList() {
            return Arrays.asList();
        }
    }
    public static class InventoryTestData{
        public static final String INVENTORY1_ID = "1";
        public static final String INVENTORY2_ID = "2";
        public static final String INVENTORY1_PRODUCT_ID = "1";
        public static final String INVENTORY2_PRODUCT_ID = "2";
        public static final int INVENTORY1_QUANTITY = 10;
        public static final int INVENTORY2_QUANTITY = 100;
        public static final int INVENTORY1_OP1_QUANTITY = 7;
        public static final int INVENTORY2_OP1_QUANTITY = 100;
        public static final int INVENTORY1_SAFE_QUANTITY = 2;
        public static final int INVENTORY2_SAFE_QUANTITY = 10;

        public static Inventory Inventory1Request = new Inventory(INVENTORY1_PRODUCT_ID, INVENTORY1_QUANTITY, INVENTORY1_SAFE_QUANTITY);

        public static Inventory getInventory1Response() {
            Inventory inventory = new Inventory(INVENTORY1_PRODUCT_ID, INVENTORY1_QUANTITY, INVENTORY1_SAFE_QUANTITY);
            inventory.setId(INVENTORY1_ID);
            return inventory;
        }
        public static Inventory getInventory2Response() {
            Inventory inventory = new Inventory(INVENTORY2_PRODUCT_ID, INVENTORY2_QUANTITY, INVENTORY2_SAFE_QUANTITY);
            inventory.setId(INVENTORY2_ID);
            return inventory;
        }
        public static Inventory Inventory1RequestOp1= new Inventory(INVENTORY1_PRODUCT_ID, INVENTORY1_OP1_QUANTITY, INVENTORY1_SAFE_QUANTITY);


        public static Inventory getInventory1ResponseOp1() {
            Inventory inventory = new Inventory(INVENTORY1_PRODUCT_ID, INVENTORY1_OP1_QUANTITY, INVENTORY1_SAFE_QUANTITY);
            inventory.setId(INVENTORY1_ID);
            return inventory;
        }
        public static Inventory getInventory2ResponseOp1() {
            Inventory inventory = new Inventory(INVENTORY2_PRODUCT_ID, INVENTORY2_OP1_QUANTITY, INVENTORY2_SAFE_QUANTITY);
            inventory.setId(INVENTORY2_ID);
            return inventory;
        }

        public static List<Inventory> getInventoryListResponse() {
            return Arrays.asList(getInventory1Response(), getInventory2Response());
        }
        public static List<Inventory> getInventoryListResponseOp1() {
            List <Inventory> inventoryList = Arrays.asList(getInventory1ResponseOp1(), getInventory2ResponseOp1());
            return inventoryList;
        }
        public static List<Inventory> getEmptyInventoryList() {
            return Arrays.asList();
        }
    }
    public static class OrderTestData{
        public static ProductsCatalog Order1Request = new ProductsCatalog(Arrays.asList(
                    new ProductsCatalog.ProductData(TestUtil.ProductTestData.PRODUCT1_ID, 10, 10.0,0),
                    new ProductsCatalog.ProductData(TestUtil.ProductTestData.PRODUCT2_ID, 100, 5.0,0)
            ));
        public static Order Order1Response = new Order(Arrays.asList(
                    new Order.ProductDetails(TestUtil.ProductTestData.PRODUCT1_ID, 10, 10.0),
                    new Order.ProductDetails(TestUtil.ProductTestData.PRODUCT2_ID, 100, 5.0)
            ), Order.OrderType.PURCHASE_ORDER, 550.0);

        public static ProductsCatalog Order2Request =new ProductsCatalog(Arrays.asList(
                    new ProductsCatalog.ProductData(TestUtil.ProductTestData.PRODUCT1_ID, 2, 10.0,0),
                    new ProductsCatalog.ProductData(TestUtil.ProductTestData.PRODUCT2_ID, 50, 4.5,0)
            ));
        public static Order Order2Response = new Order(Arrays.asList(
                    new Order.ProductDetails(TestUtil.ProductTestData.PRODUCT1_ID, 2, 10.0),
                    new Order.ProductDetails(TestUtil.ProductTestData.PRODUCT2_ID, 50, 4.5)
            ), Order.OrderType.SALE_ORDER, 245.0);
        public static ProductsCatalog OrderQuantityZeroRequest = new ProductsCatalog(Arrays.asList(
                    new ProductsCatalog.ProductData(TestUtil.ProductTestData.PRODUCT1_ID, 0, 10.0,0)
            ));
    }

    public static class AuditTestData{

        public static ResponseEntity<List<Inventory>> getInventoryListResponse() {
            /*
            [
                {
                    "id": "65bfc4cca2c8b35afd9ac230",
                    "productId": "65bfc4b5a2c8b35afd9ac22e",
                    "quantity": 0,
                    "safeQuantity": 2,
                    "lastTime": "2024-02-04T22:39:55.969"
                },
                {
                    "id": "65bfc4cca2c8b35afd9ac231",
                    "productId": "65bfc4c1a2c8b35afd9ac22f",
                    "quantity": 50,
                    "safeQuantity": 20,
                    "lastTime": "2024-02-04T22:39:46.758"
                }
             ]
             */
            Inventory inventory1 = new Inventory("65bfc4b5a2c8b35afd9ac22e", 0, 2);
            inventory1.setId("65bfc4cca2c8b35afd9ac230");
            Inventory inventory2 = new Inventory("65bfc4c1a2c8b35afd9ac22f", 50, 20);
            inventory2.setId("65bfc4cca2c8b35afd9ac231");
            return new ResponseEntity<>(Arrays.asList(inventory1, inventory2), HttpStatus.OK);
        }
        public static ResponseEntity<List<Product>> getProductListResponse() {
            /*
            [
                {
                    "id": "65bfc4b5a2c8b35afd9ac22e",
                    "name": "Milk",
                    "price": 12.0,
                    "category": "Dairy",
                    "lastTime": "2024-02-04T22:39:53.321"
                },
                {
                    "id": "65bfc4c1a2c8b35afd9ac22f",
                    "name": "Salt",
                    "price": 5.0,
                    "category": "Grocery",
                    "lastTime": "2024-02-04T22:39:21.075"
                },
                {
                    "id": "65c0069fd811547d13f21e70",
                    "name": "System Design Book",
                    "price": 300.0,
                    "category": "Book",
                    "lastTime": "2024-02-05T03:20:23.748"
                }
            ]
             */
            Product product1 = new Product("Milk", 12.0, "Dairy");
            product1.setId("65bfc4b5a2c8b35afd9ac22e");
            Product product2 = new Product("Salt", 5.0, "Grocery");
            product2.setId("65bfc4c1a2c8b35afd9ac22f");
            Product product3 = new Product("System Design Book", 300.0, "Book");
            product3.setId("65c0069fd811547d13f21e70");
            return ResponseEntity.ok(Arrays.asList(product1, product2, product3));
        }
        public static ResponseEntity<List<Order>> getOrderListResponse() {
            /*
            [
                {
                    "id": "65bfc4cca2c8b35afd9ac232",
                    "type": "PURCHASE_ORDER",
                    "productCatalog": [
                        {
                            "productId": "65bfc4b5a2c8b35afd9ac22e",
                            "quantity": 10,
                            "price": 10.0
                        },
                        {
                            "productId": "65bfc4c1a2c8b35afd9ac22f",
                            "quantity": 100,
                            "price": 5.0
                        }
                    ],
                    "totalPrice": 600.0,
                    "lastTime": "2024-02-04T22:39:32.826"
                },
                {
                    "id": "65bfc4d4a2c8b35afd9ac233",
                    "type": "SALE_ORDER",
                    "productCatalog": [
                        {
                            "productId": "65bfc4b5a2c8b35afd9ac22e",
                            "quantity": 3,
                            "price": 10.0
                        }
                    ],
                    "totalPrice": 30.0,
                    "lastTime": "2024-02-04T22:39:40.283"
                },
                {
                    "id": "65bfc4daa2c8b35afd9ac234",
                    "type": "SALE_ORDER",
                    "productCatalog": [
                        {
                            "productId": "65bfc4b5a2c8b35afd9ac22e",
                            "quantity": 2,
                            "price": 10.0
                        },
                        {
                            "productId": "65bfc4c1a2c8b35afd9ac22f",
                            "quantity": 50,
                            "price": 4.5
                        }
                    ],
                    "totalPrice": 245.0,
                    "lastTime": "2024-02-04T22:39:46.837"
                },
                {
                    "id": "65bfc4e4a2c8b35afd9ac235",
                    "type": "SALE_ORDER",
                    "productCatalog": [
                        {
                            "productId": "65bfc4b5a2c8b35afd9ac22e",
                            "quantity": 5,
                            "price": 12.0
                        }
                    ],
                    "totalPrice": 60.0,
                    "lastTime": "2024-02-04T22:39:56.007"
                }
            ]
             */
            Order order1 = new Order(Arrays.asList(
                    new Order.ProductDetails("65bfc4b5a2c8b35afd9ac22e", 10, 10.0),
                    new Order.ProductDetails("65bfc4c1a2c8b35afd9ac22f", 100, 5.0)
            ), Order.OrderType.PURCHASE_ORDER, 600.0);
            order1.setId("65bfc4cca2c8b35afd9ac232");
            Order order2 = new Order(Arrays.asList(
                    new Order.ProductDetails("65bfc4b5a2c8b35afd9ac22e", 3, 10.0)
            ), Order.OrderType.SALE_ORDER, 30.0);
            order2.setId("65bfc4d4a2c8b35afd9ac233");
            Order order3 = new Order(Arrays.asList(
                    new Order.ProductDetails("65bfc4b5a2c8b35afd9ac22e", 2, 10.0),
                    new Order.ProductDetails("65bfc4c1a2c8b35afd9ac22f", 50, 4.5)
            ), Order.OrderType.SALE_ORDER, 245.0);
order3.setId("65bfc4daa2c8b35afd9ac234");
            Order order4 = new Order(Arrays.asList(
                    new Order.ProductDetails("65bfc4b5a2c8b35afd9ac22e", 5, 12.0)
            ), Order.OrderType.SALE_ORDER, 60.0);
            order4.setId("65bfc4e4a2c8b35afd9ac235");
            return ResponseEntity.ok(Arrays.asList(order1, order2, order3, order4));
        }
        public static Audit getAuditResponse() {
            /*
            {
                "sales": {
                    "orderCount": 3,
                    "uniqueProductCount": 2,
                    "totalItemCount": 60,
                    "totalIncome": 335.0,
                    "productDetails": [
                        {
                            "productId": "65bfc4b5a2c8b35afd9ac22e",
                            "productName": "Milk",
                            "itemCount": 10,
                            "totalIncome": 110.0,
                            "incomePerItem": 11.0
                        },
                        {
                            "productId": "65bfc4c1a2c8b35afd9ac22f",
                            "productName": "Salt",
                            "itemCount": 50,
                            "totalIncome": 225.0,
                            "incomePerItem": 4.5
                        }
                    ]
                },
                "purchase": {
                    "orderCount": 1,
                    "uniqueProductCount": 2,
                    "totalItemCount": 110,
                    "totalExpenditure": 600.0,
                    "productDetails": [
                        {
                            "productId": "65bfc4b5a2c8b35afd9ac22e",
                            "productName": "Milk",
                            "itemCount": 10,
                            "totalExpenditure": 100.0,
                            "expenditurePerItem": 10.0
                        },
                        {
                            "productId": "65bfc4c1a2c8b35afd9ac22f",
                            "productName": "Salt",
                            "itemCount": 100,
                            "totalExpenditure": 500.0,
                            "expenditurePerItem": 5.0
                        }
                    ]
                },
                "profit": {
                    "totalProfit": -15.0,
                    "productDetails": [
                        {
                            "productId": "65bfc4b5a2c8b35afd9ac22e",
                            "productName": "Milk",
                            "totalProfit": 10.0,
                            "profitPerItem": 1.0
                        },
                        {
                            "productId": "65bfc4c1a2c8b35afd9ac22f",
                            "productName": "Salt",
                            "totalProfit": -25.0,
                            "profitPerItem": -0.5
                        }
                    ]
                },
                "productWithoutInventory": {
                    "countOfProduct": 1,
                    "productDetails": [
                        {
                            "productId": "65c0069fd811547d13f21e70",
                            "productName": "System Design Book",
                            "productCategory": "Book"
                        }
                    ]
                },
                "productWithStockLessThanSafeQuantity": {
                    "countOfProduct": 1,
                    "totalExpenditureNeeded": 24.0,
                    "productDetails": [
                        {
                            "productId": "65bfc4b5a2c8b35afd9ac22e",
                            "productName": "Milk",
                            "currentStock": 0,
                            "safeQuantity": 2,
                            "stockNeeded": 2,
                            "expenditureNeeded": 24.0
                        }
                    ]
                },
                "auditTime": "2024-02-05T03:21:11.404102"
            }
             */
            Audit.Sales sales= new Audit.Sales(3,  60, 335.0,
            Arrays.asList(
                    new Audit.Sales.ProductSales("65bfc4b5a2c8b35afd9ac22e", "Milk", 10, 110.0, 11.0),
                    new Audit.Sales.ProductSales("65bfc4c1a2c8b35afd9ac22f", "Salt", 50, 225.0, 4.5)
            ));
            Audit.Purchase purchase= new Audit.Purchase(1,110,600.0,
            Arrays.asList(
                    new Audit.Purchase.ProductPurchase("65bfc4b5a2c8b35afd9ac22e", "Milk", 10, 100.0, 10.0),
                    new Audit.Purchase.ProductPurchase("65bfc4c1a2c8b35afd9ac22f", "Salt", 100, 500.0, 5.0)
            ));
            Audit.Profit profit= new Audit.Profit(-15.0,
    Arrays.asList(
                    new Audit.Profit.ProductProfit("65bfc4b5a2c8b35afd9ac22e", "Milk", 10.0, 1.0),
                    new Audit.Profit.ProductProfit("65bfc4c1a2c8b35afd9ac22f", "Salt", -25.0, -0.5)
            ));
            Audit.ProductWithoutInventory productWithoutInventory= new Audit.ProductWithoutInventory(
    Arrays.asList(
                    new Audit.ProductWithoutInventory.ProductDetails("65c0069fd811547d13f21e70", "System Design Book", "Book")
            ));
            Audit.ProductWithStockLessThanSafeQuantity productWithStockLessThanSafeQuantity= new Audit.ProductWithStockLessThanSafeQuantity(
    Arrays.asList(
                    new Audit.ProductWithStockLessThanSafeQuantity.ProductDetails("65bfc4b5a2c8b35afd9ac22e", "Milk", 0, 2, 2, 24.0)
            ));
        return new Audit(
                    sales,
                    purchase,
                    profit,
                    productWithoutInventory,
                    productWithStockLessThanSafeQuantity
            );
        }
    }
}