package com.nextuple.pranay.backend;

import com.nextuple.pranay.backend.controller.input.ProductsCatalog;
import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;

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
}