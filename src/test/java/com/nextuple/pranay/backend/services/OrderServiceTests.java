package com.nextuple.pranay.backend.services;

import com.nextuple.pranay.backend.TestUtil;
import com.nextuple.pranay.backend.controller.input.ProductsCatalog;
import com.nextuple.pranay.backend.exception.CustomException;
import com.nextuple.pranay.backend.repo.OrderRepo;
import com.nextuple.pranay.backend.model.Order;
import com.nextuple.pranay.backend.service.InventoryServices;
import com.nextuple.pranay.backend.service.OrderServices;
import com.nextuple.pranay.backend.service.ProductServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
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
    public void testCreatePurchaseOrder_Success() {
        when(productServices.findProductById("1")).thenReturn(TestUtil.ProductTestData.getProduct1Response());
        when(productServices.findProductById("2")).thenReturn(TestUtil.ProductTestData.getProduct2Response());
        when(inventoryServices.findInventoryByProductId("1")).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory1Response(), HttpStatus.OK));
        when(inventoryServices.findInventoryByProductId("2")).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory2Response(), HttpStatus.OK));
        when(inventoryServices.updateInventories(any(List.class))).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
        when(orderRepo.save(any(Order.class))).thenReturn(TestUtil.OrderTestData.Order1Response);

        ResponseEntity<Order> response = orderServices.createPurchaseOrder(TestUtil.OrderTestData.Order1Request);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    public void testCreatePurchaseOrder_ProductNotFound() {
        when(productServices.findProductById(anyString())).thenReturn(null);
        assertThrows(CustomException.ProductNotFoundException.class, () -> {
            orderServices.createPurchaseOrder(TestUtil.OrderTestData.Order1Request);
        });
    }
    @Test
    public void testCreatePurchaseOrder_InventoryNotFound() {
        when(productServices.findProductById("1")).thenReturn(TestUtil.ProductTestData.getProduct1Response());
        when(productServices.findProductById("2")).thenReturn(TestUtil.ProductTestData.getProduct2Response());
        when(inventoryServices.findInventoryByProductId("1")).thenThrow(new CustomException.InventoryNotFoundException("Inventory not found"));
        when(inventoryServices.findInventoryByProductId("2")).thenThrow(new CustomException.InventoryNotFoundException("Inventory not found"));
        when(inventoryServices.updateInventories(any(List.class))).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
        when(orderRepo.save(any(Order.class))).thenReturn(TestUtil.OrderTestData.Order1Response);

        ResponseEntity<Order> response = orderServices.createPurchaseOrder(TestUtil.OrderTestData.Order1Request);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    public void testCreatePurchaseOrder_EmptyProductCatalog() {
        ProductsCatalog emptyProductsCatalog = new ProductsCatalog();
        assertThrows(CustomException.ProductCatalogNotFoundExpception.class, () -> {
            orderServices.createPurchaseOrder(emptyProductsCatalog);
        });
    }
    @Test
    public void testCreatePurchaseOrder_QuantityZeroOrNegative() {
        when(productServices.findProductById("1")).thenReturn(TestUtil.ProductTestData.getProduct1Response());

        assertThrows(CustomException.QuantityIsZeroOrNegativeException.class, () -> {
            orderServices.createPurchaseOrder(TestUtil.OrderTestData.OrderQuantityZeroRequest);
        });
    }
    @Test
    public void testCreatePurchaseOrder_InventoryUpdateFailed() {
        when(productServices.findProductById("1")).thenReturn(TestUtil.ProductTestData.getProduct1Response());
        when(productServices.findProductById("2")).thenReturn(TestUtil.ProductTestData.getProduct2Response());
        when(inventoryServices.findInventoryByProductId("1")).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory1Response(), HttpStatus.OK));
        when(inventoryServices.findInventoryByProductId("2")).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory2Response(), HttpStatus.OK));
        when(inventoryServices.updateInventories(any(List.class))).thenThrow(new CustomException.SaveNotSuccessfulException("Inventory update failed"));
        when(orderRepo.save(any(Order.class))).thenReturn(TestUtil.OrderTestData.Order1Response);
        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            orderServices.createPurchaseOrder(TestUtil.OrderTestData.Order1Request);
        });
    }

    @Test
    public void testCreatePurchaseOrder_SaveNotSuccessful() {
        when(productServices.findProductById("1")).thenReturn(TestUtil.ProductTestData.getProduct1Response());
        when(productServices.findProductById("2")).thenReturn(TestUtil.ProductTestData.getProduct2Response());
        when(inventoryServices.findInventoryByProductId("1")).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory1Response(), HttpStatus.OK));
        when(inventoryServices.findInventoryByProductId("2")).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory2Response(), HttpStatus.OK));
        when(inventoryServices.updateInventories(any(List.class))).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
        when(orderRepo.save(any(Order.class))).thenThrow(new RuntimeException("A database error"));

        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            orderServices.createPurchaseOrder(TestUtil.OrderTestData.Order1Request);
        });
    }

    @Test
    public void testCreateSaleOrder_Success() {
        when(productServices.findProductById("1")).thenReturn(TestUtil.ProductTestData.getProduct1Response());
        when(productServices.findProductById("2")).thenReturn(TestUtil.ProductTestData.getProduct2Response());
        when(inventoryServices.findInventoryByProductId("1")).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory1Response(), HttpStatus.OK));
        when(inventoryServices.findInventoryByProductId("2")).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory2Response(), HttpStatus.OK));
        when(inventoryServices.updateInventories(any(List.class))).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
        when(orderRepo.save(any(Order.class))).thenReturn(TestUtil.OrderTestData.Order2Response);
        ResponseEntity<Order> response = orderServices.createSaleOrder(TestUtil.OrderTestData.Order2Request);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    public void testCreateSaleOrder_QuantityZeroOrNegative() {
        when(productServices.findProductById("1")).thenReturn(TestUtil.ProductTestData.getProduct1Response());
        assertThrows(CustomException.QuantityIsZeroOrNegativeException.class, () -> {
            orderServices.createSaleOrder(TestUtil.OrderTestData.OrderQuantityZeroRequest);
        });
    }
    @Test
    public void testCreateSaleOrder_ProductNotFound() {
        when(productServices.findProductById(anyString())).thenReturn(null);
        assertThrows(CustomException.ProductNotFoundException.class, () -> {
            orderServices.createSaleOrder(TestUtil.OrderTestData.Order2Request);
        });
    }
    @Test
    public void testCreateSaleOrder_InsufficientInventory() {
        when(productServices.findProductById(anyString())).thenReturn(TestUtil.ProductTestData.getProduct1Response());
        when(inventoryServices.findInventoryByProductId(anyString())).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory1Response(), HttpStatus.OK));

        assertThrows(CustomException.QuantityNotAvailableException.class, () -> {
            orderServices.createSaleOrder(TestUtil.OrderTestData.Order2Request);
        });
    }
    @Test
    public void testCreateSaleOrder_InventoryNotFound() {
        when(productServices.findProductById("1")).thenReturn(TestUtil.ProductTestData.getProduct1Response());
        when(productServices.findProductById("2")).thenReturn(TestUtil.ProductTestData.getProduct2Response());
        when(inventoryServices.findInventoryByProductId(anyString())).thenReturn(
                new ResponseEntity<>(null, HttpStatus.OK));
        when(inventoryServices.updateInventories(any(List.class))).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
        when(orderRepo.save(any(Order.class))).thenReturn(TestUtil.OrderTestData.Order2Response);
        assertThrows(CustomException.InventoryNotFoundException.class, () -> {
            orderServices.createSaleOrder(TestUtil.OrderTestData.Order2Request);
        });
    }
    @Test
    public void testCreateSaleOrder_EmptyProductCatalog() {
        ProductsCatalog emptyProductsCatalog = new ProductsCatalog();
        assertThrows(CustomException.ProductCatalogNotFoundExpception.class, () -> {
            orderServices.createSaleOrder(emptyProductsCatalog);
        });
    }
    @Test
    public void testCreateSaleOrder_SaveNotSuccessfulException() {
        when(productServices.findProductById("1")).thenReturn(TestUtil.ProductTestData.getProduct1Response());
        when(productServices.findProductById("2")).thenReturn(TestUtil.ProductTestData.getProduct2Response());
        when(inventoryServices.findInventoryByProductId("1")).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory1Response(), HttpStatus.OK));
        when(inventoryServices.findInventoryByProductId("2")).thenReturn(
                new ResponseEntity<>(TestUtil.InventoryTestData.getInventory2Response(), HttpStatus.OK));
        when(inventoryServices.updateInventories(any(List.class))).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
        when(orderRepo.save(any(Order.class))).thenThrow(new RuntimeException("A database error"));
        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            orderServices.createSaleOrder(TestUtil.OrderTestData.Order2Request);
        });
    }
    @Test
    public void testListAllOrders_Success() {
        when(orderRepo.findAll()).thenReturn(Arrays.asList(TestUtil.OrderTestData.Order1Response, TestUtil.OrderTestData.Order2Response));
        ResponseEntity<List<Order>> response = orderServices.listAllOrders();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
    @Test
    public void testListAllOrders_NoOrdersFound() {
        when(orderRepo.findAll()).thenReturn(Arrays.asList());
        assertThrows(CustomException.OrderNotFoundException.class, () -> {
            orderServices.listAllOrders();
        });
    }
    @Test
    public void testFindOrderById_Success() {
        when(orderRepo.findById("1")).thenReturn(java.util.Optional.of(TestUtil.OrderTestData.Order1Response));
        ResponseEntity<Order> response = orderServices.findOrderById("1");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    public void testFindOrderById_OrderNotFound() {
        when(orderRepo.findById("1")).thenReturn(java.util.Optional.empty());
        assertThrows(CustomException.OrderNotFoundException.class, () -> {
            orderServices.findOrderById("1");
        });
    }
    @Test
    public void testFindOrderByProductId_Success() {
        when(orderRepo.findOrdersByProductId("1")).thenReturn(Arrays.asList(TestUtil.OrderTestData.Order1Response));
        ResponseEntity<List<Order>> response = orderServices.findOrderByProductId("1");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
    @Test
    public void testFindOrderByProductId_OrderNotFound() {
        when(orderRepo.findOrdersByProductId("1")).thenReturn(Arrays.asList());
        assertThrows(CustomException.OrderNotFoundException.class, () -> {
            orderServices.findOrderByProductId("1");
        });
    }
    @Test
    public void testDeleteOrderById_Success() {
        when(orderRepo.findById("1")).thenReturn(java.util.Optional.of(TestUtil.OrderTestData.Order1Response));
        orderServices.deleteOrderById("1");
        when(orderRepo.findById("1")).thenReturn(java.util.Optional.empty());
        assertThrows(CustomException.OrderNotFoundException.class, () -> {
            orderServices.findOrderById("1");
        });
    }
    @Test
    public void testDeleteOrderById_OrderNotFound() {
        when(orderRepo.findById("1")).thenReturn(java.util.Optional.empty());
        assertThrows(CustomException.OrderNotFoundException.class, () -> {
            orderServices.deleteOrderById("1");
        });
    }
    @Test
    public void testDeleteOrderById_SaveNotSuccessful() {
        when(orderRepo.findById("1")).thenReturn(java.util.Optional.of(TestUtil.OrderTestData.Order1Response));
        doThrow(new RuntimeException("A database error")).when(orderRepo).deleteById("1");
        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            orderServices.deleteOrderById("1");
        });
    }
}