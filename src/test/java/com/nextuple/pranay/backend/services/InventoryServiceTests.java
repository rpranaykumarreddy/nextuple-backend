package com.nextuple.pranay.backend.services;

import ch.qos.logback.core.helpers.CyclicBuffer;
import com.nextuple.pranay.backend.TestUtil;
import com.nextuple.pranay.backend.exception.CustomException;
import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.repo.InventoryRepo;
import com.nextuple.pranay.backend.service.InventoryServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class InventoryServiceTests {
    @Mock
    private InventoryRepo inventoryRepo;
    @Mock
    private Logger logger;
    @InjectMocks
    private InventoryServices inventoryServices;

    @Test
    public void testCreateInventory_Success() {
        Inventory inventory = TestUtil.InventoryTestData.Inventory1Request;
        when(inventoryRepo.save(inventory)).thenReturn(TestUtil.InventoryTestData.getInventory1Response());
        ResponseEntity<Inventory> responseEntity = inventoryServices.createInventory(inventory);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
assertEquals(TestUtil.InventoryTestData.INVENTORY1_PRODUCT_ID, responseEntity.getBody().getProductId());
        verify(logger, never()).error(anyString());
    }
    @Test
    public void testCreateInventory_SaveNotSuccessfulException() {
        Inventory inventory = TestUtil.InventoryTestData.Inventory1Request;
        when(inventoryRepo.save(inventory)).thenThrow(new RuntimeException("A database error"));
        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            inventoryServices.createInventory(inventory);
        });
    }
    @Test
    public void testCreateInventory_InventoryAlreadyExistsException() {
        Inventory inventory = TestUtil.InventoryTestData.getInventory1Response();
        when(inventoryRepo.findAllByProductId(inventory.getProductId())).thenReturn(Arrays.asList(inventory));
        assertThrows(CustomException.InventoryAlreadyExistsException.class, () -> {
            inventoryServices.createInventory(inventory);
        });
    }
    @Test
    public void testFindInventoryById_Success() {
        Inventory inventory = TestUtil.InventoryTestData.getInventory1Response();
        when(inventoryRepo.findById(TestUtil.InventoryTestData.INVENTORY1_ID)).thenReturn(java.util.Optional.of(inventory));
        ResponseEntity<Inventory> responseEntity = inventoryServices.findInventoryById(TestUtil.InventoryTestData.INVENTORY1_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TestUtil.InventoryTestData.INVENTORY1_PRODUCT_ID, responseEntity.getBody().getProductId());
        verify(logger, never()).error(anyString());
    }
    @Test
    public void testFindInventoryById_InventoryNotFoundException() {
        when(inventoryRepo.findById(TestUtil.InventoryTestData.INVENTORY1_ID)).thenReturn(java.util.Optional.empty());
        assertThrows(CustomException.InventoryNotFoundException.class, () -> {
            inventoryServices.findInventoryById(TestUtil.InventoryTestData.INVENTORY1_ID);
        });
    }
    @Test
    public void testFindInventoryByProductId_Success() {
        when(inventoryRepo.findAllByProductId(TestUtil.InventoryTestData.INVENTORY1_PRODUCT_ID)).thenReturn(Arrays.asList(TestUtil.InventoryTestData.getInventory1Response()));
        ResponseEntity responseEntity = inventoryServices.findInventoryByProductId(TestUtil.InventoryTestData.INVENTORY1_PRODUCT_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(logger, never()).error(anyString());
    }
    @Test
    public void testFindInventoryByProductId_InventoryNotFoundException() {
        when(inventoryRepo.findAllByProductId(TestUtil.InventoryTestData.INVENTORY1_PRODUCT_ID)).thenReturn(Arrays.asList());
        assertThrows(CustomException.InventoryNotFoundException.class, () -> {
            inventoryServices.findInventoryByProductId(TestUtil.InventoryTestData.INVENTORY1_PRODUCT_ID);
        });
    }
    @Test
    public void testUpdateInventoryById_Success() {
        when(inventoryRepo.findById(TestUtil.InventoryTestData.INVENTORY1_ID)).thenReturn(java.util.Optional.of(TestUtil.InventoryTestData.getInventory1Response()));
        when(inventoryRepo.save(any(Inventory.class))).thenReturn(TestUtil.InventoryTestData.getInventory1ResponseOp1());
        ResponseEntity<Inventory> responseEntity = inventoryServices.updateInventoryById(TestUtil.InventoryTestData.INVENTORY1_ID, TestUtil.InventoryTestData.Inventory1RequestOp1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TestUtil.InventoryTestData.INVENTORY1_OP1_QUANTITY, responseEntity.getBody().getQuantity());
        verify(logger, never()).error(anyString());
    }
    @Test
    public void testUpdateInventoryById_InventoryNotFoundException() {
        when(inventoryRepo.findById(TestUtil.InventoryTestData.INVENTORY1_ID)).thenReturn(java.util.Optional.empty());
        assertThrows(CustomException.InventoryNotFoundException.class, () -> {
            inventoryServices.updateInventoryById(TestUtil.InventoryTestData.INVENTORY1_ID, TestUtil.InventoryTestData.Inventory1RequestOp1);
        });
    }
    @Test
    public void testUpdateInventoryById_SaveNotSuccessfulException() {
        when(inventoryRepo.findById(TestUtil.InventoryTestData.INVENTORY1_ID)).thenReturn(java.util.Optional.of(TestUtil.InventoryTestData.getInventory1Response()));
        when(inventoryRepo.save(any(Inventory.class))).thenThrow(new RuntimeException("A database error"));
        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            inventoryServices.updateInventoryById(TestUtil.InventoryTestData.INVENTORY1_ID, TestUtil.InventoryTestData.Inventory1RequestOp1);
        });
    }
    @Test
    public void testDeleteInventoryById_Success() {
        when(inventoryRepo.findById(TestUtil.InventoryTestData.INVENTORY1_ID)).thenReturn(java.util.Optional.of(TestUtil.InventoryTestData.getInventory1Response()));
        ResponseEntity<Boolean> responseEntity = inventoryServices.deleteInventoryById(TestUtil.InventoryTestData.INVENTORY1_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody());
        verify(inventoryRepo, times(1)).deleteById(TestUtil.InventoryTestData.INVENTORY1_ID);
        verify(logger, never()).error(anyString());
    }
    @Test
    public void testDeleteInventoryById_InventoryNotFoundException() {
        when(inventoryRepo.findById(TestUtil.InventoryTestData.INVENTORY1_ID)).thenReturn(java.util.Optional.empty());
        assertThrows(CustomException.InventoryNotFoundException.class, () -> {
            inventoryServices.deleteInventoryById(TestUtil.InventoryTestData.INVENTORY1_ID);
        });
    }
    @Test
    public void testDeleteInventoryById_SaveNotSuccessfulException() {
        when(inventoryRepo.findById(TestUtil.InventoryTestData.INVENTORY1_ID)).thenReturn(java.util.Optional.of(TestUtil.InventoryTestData.getInventory1Response()));
        doThrow(new RuntimeException("A database error")).when(inventoryRepo).deleteById(TestUtil.InventoryTestData.INVENTORY1_ID);
        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            inventoryServices.deleteInventoryById(TestUtil.InventoryTestData.INVENTORY1_ID);
        });
    }
    @Test
    public void testListAllInventory_Success() {
        when(inventoryRepo.findAll()).thenReturn(TestUtil.InventoryTestData.getInventoryListResponse());
        ResponseEntity responseEntity = inventoryServices.listAllInventory();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(logger, never()).error(anyString());
    }
    @Test
    public void testListAllInventory_EmptyList() {
        when(inventoryRepo.findAll()).thenReturn(TestUtil.InventoryTestData.getEmptyInventoryList());
        assertThrows(CustomException.InventoryNotFoundException.class, () -> {
            inventoryServices.listAllInventory();
        });
    }
    @Test
    public void testUpdateInventories_Success() {
        when(inventoryRepo.saveAll(TestUtil.InventoryTestData.getInventoryListResponseOp1())).thenReturn(TestUtil.InventoryTestData.getInventoryListResponseOp1());
        ResponseEntity<Boolean> responseEntity = inventoryServices.updateInventories(TestUtil.InventoryTestData.getInventoryListResponseOp1());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody());
        verify(logger, never()).error(anyString());
    }
    @Test
    public void testUpdateInventories_SaveNotSuccessfulException() {
        List<Inventory> inventoryList = TestUtil.InventoryTestData.getInventoryListResponseOp1();
        when(inventoryRepo.saveAll(inventoryList)).thenThrow(new RuntimeException("A database error"));
        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            inventoryServices.updateInventories(inventoryList);
        });
    }

}