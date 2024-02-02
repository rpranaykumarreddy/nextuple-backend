package com.nextuple.pranay.backend.services;

import com.nextuple.pranay.backend.TestUtil;
import com.nextuple.pranay.backend.repo.OrderRepo;
import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.model.Product;
import com.nextuple.pranay.backend.service.InventoryServices;
import com.nextuple.pranay.backend.service.OrderServices;
import com.nextuple.pranay.backend.service.ProductServices;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTests {
    @Mock
    private OrderRepo orderRepo;
    @Mock
    private ProductServices productServices;
    @Mock
    private InventoryServices inventoryServices;
    @Mock
    private Logger logger;
    @InjectMocks
    private OrderServices orderServices;

    @Test
    public void testCreateSaleOrder_Success() {
        List<Order.ProductDetails> productCatalog = TestUtil.OrderTestData.ORDER1_PRODUCT_CATALOG;

        Product product1 = TestUtil.ProductTestData.getProduct1Response();
        Product product2 = TestUtil.ProductTestData.getProduct2Response();
        when(productServices.findProductById(TestUtil.ProductTestData.PRODUCT1_ID)).thenReturn(product1);
        when(productServices.findProductById(TestUtil.ProductTestData.PRODUCT2_ID)).thenReturn(product2);

        when(inventoryServices.findInventoryByProductId(TestUtil.ProductTestData.PRODUCT1_ID))
                .thenReturn(new ResponseEntity<>(Arrays.asList(TestUtil.InventoryTestData.getInventory1Response()), HttpStatus.OK));
        when(inventoryServices.findInventoryByProductId(TestUtil.ProductTestData.PRODUCT2_ID))
                .thenReturn(new ResponseEntity<>(Arrays.asList(TestUtil.InventoryTestData.getInventory2Response()), HttpStatus.OK));

        when(inventoryServices.updateInventories(TestUtil.InventoryTestData.getInventoryListResponse()))
                .thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
        when(orderRepo.save(TestUtil.OrderTestData.getOrder1Request())).thenReturn(TestUtil.OrderTestData.getOrder1Response());

        ResponseEntity<Order> response = orderServices.createSaleOrder(productCatalog);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(TestUtil.OrderTestData.getOrder1Response(), response.getBody());
    }


}