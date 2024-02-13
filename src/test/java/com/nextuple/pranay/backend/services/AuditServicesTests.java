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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(expectedAudit.hashCode(), audit.hashCode());
        //This does not check for the Timestamp has it is impossible to match

        verify(inventoryServices, times(1)).listAllInventory();
        verify(orderServices, times(1)).listAllOrders();
        verify(productServices, times(1)).listAllProducts();

    }
    @Test
    void testAudit_ForCompleteLines() {
        when(inventoryServices.listAllInventory()).thenReturn(TestUtil.ExtraAuditTestData.getInventoryListResponse());
        when(orderServices.listAllOrders()).thenReturn(TestUtil.ExtraAuditTestData.getOrderListResponse());
        when(productServices.listAllProducts()).thenReturn(TestUtil.ExtraAuditTestData.getProductListResponse());

        Audit audit = auditServices.audit().getBody();
        Audit expectedAudit = TestUtil.ExtraAuditTestData.getAuditResponse();
        assertEquals(expectedAudit, audit);
        verify(inventoryServices, times(1)).listAllInventory();
        verify(orderServices, times(1)).listAllOrders();
        verify(productServices, times(1)).listAllProducts();
    }
    @Test
    void testAuditByGetters() {
        when(inventoryServices.listAllInventory()).thenReturn(TestUtil.ExtraAuditTestData.getInventoryListResponse());
        when(orderServices.listAllOrders()).thenReturn(TestUtil.ExtraAuditTestData.getOrderListResponse());
        when(productServices.listAllProducts()).thenReturn(TestUtil.ExtraAuditTestData.getProductListResponse());
        Audit audit = auditServices.audit().getBody();
        Audit expectedAudit = TestUtil.ExtraAuditTestData.getAuditResponse();

        assertNotNull(audit);
        assertEquals(expectedAudit.getSales().hashCode(), audit.getSales().hashCode());
        assertEquals(expectedAudit.getProfit().hashCode(), audit.getProfit().hashCode());
        assertEquals(expectedAudit.getPurchase().hashCode(), audit.getPurchase().hashCode());
        assertEquals(expectedAudit.getProductWithoutInventory().hashCode(), audit.getProductWithoutInventory().hashCode());
        assertEquals(expectedAudit.getProductWithStockLessThanSafeQuantity().hashCode(), audit.getProductWithStockLessThanSafeQuantity().hashCode());
        LocalDateTime errorDetailsTimeStamp = audit.getAuditTime();
        assertTrue(errorDetailsTimeStamp.isBefore(LocalDateTime.now()) || errorDetailsTimeStamp.isEqual(LocalDateTime.now()));
    }
}