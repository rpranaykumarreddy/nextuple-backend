package com.nextuple.pranay.backend.services;

import com.nextuple.pranay.backend.TestUtil;
import com.nextuple.pranay.backend.controller.output.Audit;
import com.nextuple.pranay.backend.service.AuditServices;
import com.nextuple.pranay.backend.service.InventoryServices;
import com.nextuple.pranay.backend.service.OrderServices;
import com.nextuple.pranay.backend.service.ProductServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuditServicesTests {
    @InjectMocks
    private AuditServices auditServices;

    @Mock
    private OrderServices orderServices;

    @Mock
    private ProductServices productServices;

    @Mock
    private InventoryServices inventoryServices;

    @Test
    void testAudit() {
        when(inventoryServices.listAllInventory()).thenReturn(TestUtil.AuditTestData.getInventoryListResponse());
        when(orderServices.listAllOrders()).thenReturn(TestUtil.AuditTestData.getOrderListResponse());
        when(productServices.listAllProducts()).thenReturn(TestUtil.AuditTestData.getProductListResponse());

        Audit audit = auditServices.audit().getBody();
        Audit expectedAudit = TestUtil.AuditTestData.getAuditResponse();
        assertNotNull(audit);

        assertEquals(expectedAudit.getSales().getOrderCount(), audit.getSales().getOrderCount());
        assertEquals(expectedAudit.getSales().getUniqueProductCount(), audit.getSales().getUniqueProductCount());
        assertEquals(expectedAudit.getSales().getTotalItemCount(), audit.getSales().getTotalItemCount());
        assertEquals(expectedAudit.getSales().getTotalIncome(), audit.getSales().getTotalIncome());
        assertEquals(expectedAudit.getSales().getProductDetails().size(), audit.getSales().getProductDetails().size());

        assertEquals(expectedAudit.getPurchase().getOrderCount(), audit.getPurchase().getOrderCount());
        assertEquals(expectedAudit.getPurchase().getUniqueProductCount(), audit.getPurchase().getUniqueProductCount());
        assertEquals(expectedAudit.getPurchase().getTotalItemCount(), audit.getPurchase().getTotalItemCount());
        assertEquals(expectedAudit.getPurchase().getTotalExpenditure(), audit.getPurchase().getTotalExpenditure());
        assertEquals(expectedAudit.getPurchase().getProductDetails().size(), audit.getPurchase().getProductDetails().size());

        assertEquals(expectedAudit.getProfit().getTotalProfit(), audit.getProfit().getTotalProfit());
        assertEquals(expectedAudit.getProfit().getProductDetails().size(), audit.getProfit().getProductDetails().size());

        assertEquals(expectedAudit.getProductWithoutInventory().getCountOfProduct(), audit.getProductWithoutInventory().getCountOfProduct());
        assertEquals(expectedAudit.getProductWithoutInventory().getProductDetails().size(), audit.getProductWithoutInventory().getProductDetails().size());

        assertEquals(expectedAudit.getProductWithStockLessThanSafeQuantity().getCountOfProduct(), audit.getProductWithStockLessThanSafeQuantity().getCountOfProduct());
        assertEquals(expectedAudit.getProductWithStockLessThanSafeQuantity().getTotalExpenditureNeeded(), audit.getProductWithStockLessThanSafeQuantity().getTotalExpenditureNeeded());
        assertEquals(expectedAudit.getProductWithStockLessThanSafeQuantity().getProductDetails().size(), audit.getProductWithStockLessThanSafeQuantity().getProductDetails().size());


        verify(inventoryServices, times(1)).listAllInventory();
        verify(orderServices, times(1)).listAllOrders();
        verify(productServices, times(1)).listAllProducts();

    }
}