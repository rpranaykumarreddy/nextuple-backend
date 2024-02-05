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

        assertEquals(expectedAudit, audit);
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
}
