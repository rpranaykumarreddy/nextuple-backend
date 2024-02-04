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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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
        when(inventoryServices.listAllInventory().getBody()).thenReturn(TestUtil.AuditTestData.getInventoryListResponse().getBody());
        when(orderServices.listAllOrders()).thenReturn(TestUtil.AuditTestData.getOrderListResponse());
        when(productServices.listAllProducts()).thenReturn(TestUtil.AuditTestData.getProductListResponse());

        ResponseEntity<Audit> auditResponseEntity = auditServices.audit();
    }
}
