package com.nextuple.pranay.backend;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.bson.assertions.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BackendApplicationTests {
//	@InjectMocks
//	private AllService allService;
//	@Mock
//	private ProductRepo productRepo;
//	@Mock
//	private InventoryRepo inventoryRepo;
//	@Mock
//	private OrderRepo orderRepo;
//
//	private Product milk;
//	private Product salt;
//	@BeforeEach
//	void setUp(){
//		MockitoAnnotations.openMocks(this);
//	}
//
//	@Test
//	void testMilkProduct() {
//		Product product = new Product("Milk Packet", 10.0, "Dairy");
//
//		milk = allService.createProduct(product).getBody();
//		assertNotNull(milk);
//		assertNotNull(milk.getId());
//		assertEquals("Milk Packet", milk.getName());
//		assertEquals(10.0, milk.getPrice());
//		assertEquals("Dairy", milk.getCategory());
//	}
//
//	@Test
//	void testSaltProduct() {
//		Product product = new Product("Salt Bag", 20.0, "Grocery");
//		salt = allService.createProduct(product).getBody();
//		assertNotNull(salt);
//		assertNotNull(salt.getId());
//		assertEquals("Salt Bag", salt.getName());
//		assertEquals(20.0, salt.getPrice());
//		assertEquals("Grocery", salt.getCategory());
//	}
//	void testPurchaseOrder(){
//		List<Order.ProductDetails> pdlist = new ArrayList<>();
//		//Need the product Id's;
//	}
}
