package com.nextuple.pranay.backend;

import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;

import java.util.Arrays;
import java.util.List;

public class TestUtil {
    public static class ProductTestData {
        public static final String PRODUCT1_NAME = "Milk Packet";
        public static final String PRODUCT2_NAME = "Salt Bag";

        public static final double PRODUCT1_PRICE = 10.0;
        public static final double PRODUCT1_NEW_PRICE = 12.0;
        public static final double PRODUCT2_PRICE = 5.0;
        public static final String PRODUCT1_CATEGORY = "Dairy";
        public static final String PRODUCT2_CATEGORY = "Grocery";
        public static final String PRODUCT1_ID = "1";
        public static final String PRODUCT2_ID = "2";
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
    }
    public static class InventoryTestData{

        public static final String INVENTORY1_ID = "1";
        public static final String INVENTORY2_ID = "2";
        public static final String INVENTORY1_PRODUCT_ID = "1";
        public static final String INVENTORY2_PRODUCT_ID = "2";
        public static final int INVENTORY_LIST_SIZE = 2;
        public static final int INVENTORY1_QUANTITY = 10;
        public static final int INVENTORY2_QUANTITY = 100;
        public static final int INVENTORY1_OP1_QUANTITY = 7;
        public static final int INVENTORY2_OP1_QUANTITY = 100;
        public static final int INVENTORY1_OP2_QUANTITY = 5;
        public static final int INVENTORY2_OP2_QUANTITY = 50;
        public static final int INVENTORY1_OP3_QUANTITY = 0;
        public static final int INVENTORY2_OP3_QUANTITY = 50;
        
        public static final int INVENTORY1_SAFE_QUANTITY = 2;
        public static final int INVENTORY2_SAFE_QUANTITY = 10;

        public static Inventory getInventory1Request() {
            return new Inventory(INVENTORY1_PRODUCT_ID, INVENTORY1_QUANTITY, INVENTORY1_SAFE_QUANTITY);
        }
        public static Inventory getInventory2Request() {
            return new Inventory(INVENTORY2_PRODUCT_ID, INVENTORY2_QUANTITY, INVENTORY2_SAFE_QUANTITY);
        }
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
        public static Inventory getInventory1RequestOp1() {
            return new Inventory(INVENTORY1_PRODUCT_ID, INVENTORY1_OP1_QUANTITY, INVENTORY1_SAFE_QUANTITY);
        }
        public static Inventory getInventory2RequestOp1() {
            return new Inventory(INVENTORY2_PRODUCT_ID, INVENTORY2_OP1_QUANTITY, INVENTORY2_SAFE_QUANTITY);
        }

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
        public static Inventory getInventory1RequestOp2() {
            return new Inventory(INVENTORY1_PRODUCT_ID, INVENTORY1_OP2_QUANTITY, INVENTORY1_SAFE_QUANTITY);
        }
        public static Inventory getInventory2RequestOp2() {
            return new Inventory(INVENTORY2_PRODUCT_ID, INVENTORY2_OP2_QUANTITY, INVENTORY2_SAFE_QUANTITY);
        }
        public static Inventory getInventory1ResponseOp2() {
            Inventory inventory = new Inventory(INVENTORY1_PRODUCT_ID, INVENTORY1_OP2_QUANTITY, INVENTORY1_SAFE_QUANTITY);
            inventory.setId(INVENTORY1_ID);
            return inventory;
        }
        public static Inventory getInventory2ResponseOp2() {
            Inventory inventory = new Inventory(INVENTORY2_PRODUCT_ID, INVENTORY2_OP2_QUANTITY, INVENTORY2_SAFE_QUANTITY);
            inventory.setId(INVENTORY2_ID);
            return inventory;
        }
        public static Inventory getInventory1RequestOp3() {
            return new Inventory(INVENTORY1_PRODUCT_ID, INVENTORY1_OP3_QUANTITY, INVENTORY1_SAFE_QUANTITY);
        }
        public static Inventory getInventory2RequestOp3() {
            return new Inventory(INVENTORY2_PRODUCT_ID, INVENTORY2_OP3_QUANTITY, INVENTORY2_SAFE_QUANTITY);
        }
        public static Inventory getInventory1ResponseOp3() {
            Inventory inventory = new Inventory(INVENTORY1_PRODUCT_ID, INVENTORY1_OP3_QUANTITY, INVENTORY1_SAFE_QUANTITY);
            inventory.setId(INVENTORY1_ID);
            return inventory;
        }
        public static Inventory getInventory2ResponseOp3() {
            Inventory inventory = new Inventory(INVENTORY2_PRODUCT_ID, INVENTORY2_OP3_QUANTITY, INVENTORY2_SAFE_QUANTITY);
            inventory.setId(INVENTORY2_ID);
            return inventory;
        }
        public static List<Inventory> getInventoryListRequestOp1() {
            return Arrays.asList(getInventory1RequestOp1(), getInventory2RequestOp1());
        }
        public static List<Inventory> getInventoryListRequestOp2() {
            return Arrays.asList(getInventory1RequestOp2(), getInventory2RequestOp2());
        }
        public static List<Inventory> getInventoryListRequestOp3() {
            return Arrays.asList(getInventory1RequestOp3(), getInventory2RequestOp3());
        }
        public static List<Inventory> getInventoryListResponse() {
            return Arrays.asList(getInventory1Response(), getInventory2Response());
        }
        public static List<Inventory> getInventoryListResponseOp1() {
            List <Inventory> inventoryList = Arrays.asList(getInventory1ResponseOp1(), getInventory2ResponseOp1());
            return inventoryList;
        }
        public static List<Inventory> getInventoryListResponseOp2() {
            return Arrays.asList(getInventory1ResponseOp2(), getInventory2ResponseOp2());
        }
        public static List<Inventory> getInventoryListResponseOp3() {
            return Arrays.asList(getInventory1ResponseOp3(), getInventory2ResponseOp3());
        }

        public static List<Inventory> getEmptyInventoryList() {
            return Arrays.asList();
        }
    }
    public static class OrderTestData{
        public static final String ORDER1_ID = "1";
        public static final String ORDER2_ID = "2";
        public static final String ORDER3_ID = "3";
        public static final String ORDER4_ID = "4";
        public static final Order.OrderType ORDER1_TYPE = Order.OrderType.PURCHASE_ORDER;
        public static final Order.OrderType ORDER2_TYPE = Order.OrderType.SALE_ORDER;
        public static final Order.OrderType ORDER3_TYPE = Order.OrderType.SALE_ORDER;
        public static final Order.OrderType ORDER4_TYPE = Order.OrderType.SALE_ORDER;

        public static final List<Order.ProductDetails> ORDER1_PRODUCT_CATALOG = Arrays.asList(
                new Order.ProductDetails(ProductTestData.PRODUCT1_ID, 10, 0),
                new Order.ProductDetails(ProductTestData.PRODUCT2_ID, 100, 0));
        public static Order getOrder1Request() {
            Order order=new Order(ORDER1_PRODUCT_CATALOG, ORDER1_TYPE, 600);
            order.setId(ORDER1_ID);
            return order;
        }
        public static Order getOrder1Response() {
            Order order=new Order(ORDER1_PRODUCT_CATALOG, ORDER1_TYPE, 600);
            order.setId(ORDER1_ID);
            return order;
        }


    }

}
/*
- I will be adding 2 products (Milk Packet, Salt Bag)

I will create following Purchase Order

- [ ]  a) I have purchased 10qty of Milk Packet (10rs)
- [ ]  b) I have purchased 100qty of Salt Bag
- [ ]  Now, I fetch the current inventory details, which should reflect 10qty of Milk Packet and 100qty of Salt Bag
- [ ]  I will create a SaleOrder of 3qty of Milk Packet
- [ ]  Now, I fetch the current inventory details, which should reflect 7qty of Milk Packet and 100qty of Salt Bag
- [ ]  If I will create a SaleOrder of 160qty of Salt Bag,
    - [ ]  a) It should fail with a reason indicating that stock is not available
- [ ]  I will create a SaleOrder of 50qty of Salt Bag and 2qty of Milk Packet
- [ ]  Now, I fetch the current inventory details, which should reflect 5qty of Milk Packet and 50qty of Salt Bag
- [ ]  Now, I will fetch the list of orders / transactions
    - [ ]  a) It should reflect total 3 orders (1 purchase and 2 sale orders)
- [ ]  I will increase the Milk Packet price
- [ ]  I will create a SaleOrder of 5qty of Milk Packet
- [ ]  Now, I fetch the current inventory details, which should reflect 0qty of Milk Packet and 50qty of Salt Bag
- [ ]  Now, I will fetch the list of orders / transactions
    - [ ]  a) It should reflect total 4 orders
 */