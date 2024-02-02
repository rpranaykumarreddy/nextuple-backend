package com.nextuple.pranay.backend.services;

import com.nextuple.pranay.backend.TestUtil;
import com.nextuple.pranay.backend.exception.CustomException;
import com.nextuple.pranay.backend.model.Product;
import com.nextuple.pranay.backend.repo.ProductRepo;
import com.nextuple.pranay.backend.service.ProductServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.verification.VerificationMode;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTests {
    @Mock
    private ProductRepo productRepo;
    @Mock
    private Logger logger;
    @InjectMocks
    private ProductServices productServices;

    @Test
    public void testCreateProduct_Success() {
        Product product = TestUtil.ProductTestData.getProduct1Request();
        when(productRepo.save(product)).thenReturn(TestUtil.ProductTestData.getProduct1Response());
        ResponseEntity<Product> responseEntity = productServices.createProduct(product);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(TestUtil.ProductTestData.PRODUCT1_NAME, responseEntity.getBody().getName());
        verify(logger, never()).error(anyString());
    }

    @Test
    public void testCreateProduct_SaveNotSuccessfulException() {
        Product product = TestUtil.ProductTestData.getProduct1Request();
        when(productRepo.save(product)).thenThrow(new RuntimeException("A database error"));
        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            productServices.createProduct(product);
        });
    }
    @Test
    public void testFindProductById_Success() {
        Product product = TestUtil.ProductTestData.getProduct1Response();
        when(productRepo.findById(TestUtil.ProductTestData.PRODUCT1_ID)).thenReturn(java.util.Optional.of(product));
        Product result = productServices.findProductById(TestUtil.ProductTestData.PRODUCT1_ID);
        assertEquals(TestUtil.ProductTestData.PRODUCT1_NAME, result.getName());
    }
    @Test
    public void testFindProductById_ProductNotFoundException() {
        when(productRepo.findById(TestUtil.ProductTestData.PRODUCT1_ID)).thenReturn(java.util.Optional.empty());
        assertThrows(CustomException.ProductNotFoundException.class, () -> {
            productServices.findProductById(TestUtil.ProductTestData.PRODUCT1_ID);
        });
    }

    @Test
    public void testUpdateProductById_Success() {;
        when(productRepo.findById(TestUtil.ProductTestData.PRODUCT1_ID)).thenReturn(java.util.Optional.of(TestUtil.ProductTestData.getProduct1Response()));
        when(productRepo.save(any(Product.class))).thenReturn(TestUtil.ProductTestData.getProduct1ResponseIncreasePrice());
        ResponseEntity<Product> responseEntity = productServices.updateProductById(TestUtil.ProductTestData.PRODUCT1_ID, TestUtil.ProductTestData.getProduct1RequestIncreasePrice());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TestUtil.ProductTestData.PRODUCT1_NEW_PRICE, responseEntity.getBody().getPrice());
        verify(logger, never()).error(anyString());
    }
    @Test
    public void testUpdateProductById_ProductNotFoundException() {
        when(productRepo.findById(TestUtil.ProductTestData.PRODUCT1_ID)).thenReturn(java.util.Optional.empty());
        assertThrows(CustomException.ProductNotFoundException.class, () -> {
            productServices.updateProductById(TestUtil.ProductTestData.PRODUCT1_ID, TestUtil.ProductTestData.getProduct1RequestIncreasePrice());
        });
    }
    @Test
    public void testUpdateProductById_SaveNotSuccessfulException() {
        when(productRepo.findById(TestUtil.ProductTestData.PRODUCT1_ID)).thenReturn(java.util.Optional.of(TestUtil.ProductTestData.getProduct1Response()));
        when(productRepo.save(any(Product.class))).thenThrow(new RuntimeException("A database error"));
        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            productServices.updateProductById(TestUtil.ProductTestData.PRODUCT1_ID, TestUtil.ProductTestData.getProduct1RequestIncreasePrice());
        });
    }
    @Test
    public void testDeleteProductById_Success() {
        when(productRepo.findById(TestUtil.ProductTestData.PRODUCT1_ID)).thenReturn(java.util.Optional.of(TestUtil.ProductTestData.getProduct1Response()));
        ResponseEntity<Boolean> responseEntity = productServices.deleteProductById(TestUtil.ProductTestData.PRODUCT1_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody());
        verify(productRepo, times(1)).deleteById(TestUtil.ProductTestData.PRODUCT1_ID);
        verify(logger, never()).error(anyString());
    }
    @Test
    public void testDeleteProductById_ProductNotFoundException() {
        when(productRepo.findById(TestUtil.ProductTestData.PRODUCT1_ID)).thenReturn(java.util.Optional.empty());
        assertThrows(CustomException.ProductNotFoundException.class, () -> {
            productServices.deleteProductById(TestUtil.ProductTestData.PRODUCT1_ID);
        });
    }
    @Test
    public void testDeleteProductById_SaveNotSuccessfulException() {
        when(productRepo.findById(TestUtil.ProductTestData.PRODUCT1_ID)).thenReturn(java.util.Optional.of(TestUtil.ProductTestData.getProduct1Response()));
        doThrow(new RuntimeException("A database error")).when(productRepo).deleteById(TestUtil.ProductTestData.PRODUCT1_ID);
        assertThrows(CustomException.SaveNotSuccessfulException.class, () -> {
            productServices.deleteProductById(TestUtil.ProductTestData.PRODUCT1_ID);
        });
    }
    @Test
    public void testListAllProducts_Success() {
        when(productRepo.findAll()).thenReturn(TestUtil.ProductTestData.getProductList());
        ResponseEntity responseEntity = productServices.listAllProducts();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Product> productList = (List<Product>) responseEntity.getBody();
        assertEquals(TestUtil.ProductTestData.PRODUCT_LIST_SIZE,productList.size());
    }
    @Test
    public void testListAllProducts_ProductNotFoundException() {
        when(productRepo.findAll()).thenThrow(new RuntimeException("A database error"));
        assertThrows(CustomException.ProductNotFoundException.class, () -> {
            productServices.listAllProducts();
        });
    }
}