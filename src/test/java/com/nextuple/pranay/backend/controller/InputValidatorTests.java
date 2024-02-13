package com.nextuple.pranay.backend.controller;

import com.nextuple.pranay.backend.TestUtil;
import com.nextuple.pranay.backend.controller.input.ProductsCatalog;
import com.nextuple.pranay.backend.controller.validator.InputValidator;
import com.nextuple.pranay.backend.exception.CustomException;
import com.nextuple.pranay.backend.model.Inventory;
import com.nextuple.pranay.backend.model.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class InputValidatorTests {
    @Spy
    private InputValidator inputValidator;

    @Test
    public void testIdValidator() {
//        InputValidator.IdValidator("123");
        assertDoesNotThrow(() -> {
            InputValidator.IdValidator("123");
        });
    }

    @Test
    public void testIdValidatorBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.IdValidator("   ");
        });
    }

    @Test
    public void testIdValidatorNullError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.IdValidator(null);
        });
    }

    @Test
    public void testNameValidator() {
        assertDoesNotThrow(() -> {
            InputValidator.nameValidator("123");
        });
    }

    @Test
    public void testNameValidatorBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.nameValidator("   ");
        });
    }

    @Test
    public void testNameValidatorNullError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.nameValidator(null);
        });
    }

    @Test
    public void testNameValidatorMaxLengthError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.nameValidator("123456789012345678901234567890123456789012345678901");
        });
    }

    @Test
    public void testNameValidatorMinLengthError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.nameValidator("12");
        });
    }

    @Test
    public void testPriceValidator() {
        assertDoesNotThrow(() -> {
            InputValidator.priceValidator(123);
        });
    }

    @Test
    public void testPriceValidatorNegativeError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.priceValidator(-1);
        });
    }

    @Test
    public void testCategoryValidator() {
        assertDoesNotThrow(() -> {
            InputValidator.categoryValidator("123");
        });
    }

    @Test
    public void testCategoryValidatorBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.categoryValidator("   ");
        });
    }

    @Test
    public void testCategoryValidatorNullError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.categoryValidator(null);
        });
    }

    @Test
    public void testCategoryValidatorMaxLengthError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.categoryValidator("123456789012345678901234567890123456789012345678901");
        });
    }

    @Test
    public void testCategoryValidatorMinLengthError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.categoryValidator("12");
        });
    }

    @Test
    public void testQuantityValidator() {
        assertDoesNotThrow(() -> {
            InputValidator.quantityValidator(123);
        });
    }

    @Test
    public void testQuantityValidatorNegativeError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.quantityValidator(-1);
        });
    }

    @Test
    public void testSafeQuantityValidator() {
        assertDoesNotThrow(() -> {
            InputValidator.safeQuantityValidator(123);
        });
    }

    @Test
    public void testSafeQuantityValidatorNegativeError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.safeQuantityValidator(-1);
        });
    }

    @Test
    public void testCreateProduct() {
        assertDoesNotThrow(() -> {
            InputValidator.Products.createProduct(
                    new Product("name", 123, "category")
            );
        });
    }

    @Test
    public void testCreateProductBlankNameError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Products.createProduct(
                    new Product("   ", 123, "category")
            );
        });
    }

    @Test
    public void testCreateProductPriceError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Products.createProduct(
                    new Product("name", -1, "category")
            );
        });
    }

    @Test
    public void testCreateProductBlankCategoryError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Products.createProduct(
                    new Product("name", 123, "   ")
            );
        });
    }

    @Test
    public void testFindProductById() {
        assertDoesNotThrow(() -> {
            InputValidator.Products.findProductById("123");
        });
    }

    @Test
    public void testFindProductByIdBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Products.findProductById("   ");
        });
    }

    @Test
    public void testUpdateProductById() {
        assertDoesNotThrow(() -> {
            InputValidator.Products.updateProductById("123", new Product("name", 123, "category"));
        });
    }

    @Test
    public void testUpdateProductByIdBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Products.updateProductById("   ", new Product("name", 123, "category"));
        });
    }

    @Test
    public void testDeleteProductById() {
        assertDoesNotThrow(() -> {
            InputValidator.Products.updateProductById("123", new Product("name", 123, "category"));
        });
    }

    @Test
    public void testDeleteProductByIdBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Products.updateProductById("   ", new Product("name", 123, "category"));
        });
    }

    @Test
    public void testCreateInventory() {
        assertDoesNotThrow(() -> {
            InputValidator.Inventory.createInventory(
                    new Inventory("id", 123, 12)
            );
        });
    }

    @Test
    public void testCreateInventoryBlankNameError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Inventory.createInventory(
                    new Inventory("   ", 123, 12)
            );
        });
    }

    @Test
    public void testCreateInventoryPriceError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Inventory.createInventory(
                    new Inventory("id", -1, 12)
            );
        });
    }

    @Test
    public void testCreateInventoryBlankCategoryError() {
        assertThrows(CustomException.ValidationException.class, () -> {
                    InputValidator.Inventory.createInventory(
                            new Inventory("id", 123, -1)
                    );
                }
        );
    }
    @Test
    public void testFindInventoryById() {
        assertDoesNotThrow(() -> {
            InputValidator.Inventory.findInventoryById("123");
        });
    }
    @Test
    public void testFindInventoryByIdBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Inventory.findInventoryById("   ");
        });
    }
    @Test
    public void testUpdateInventoryById() {
        assertDoesNotThrow(() -> {
            InputValidator.Inventory.updateInventoryById("123", new Inventory("id", 123, 12));
        });
    }
    @Test
    public void testUpdateInventoryByIdBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Inventory.updateInventoryById("   ", new Inventory("id", 123, 12));
        });
    }
    @Test
    public void testDeleteInventoryById() {
        assertDoesNotThrow(() -> {
            InputValidator.Inventory.deleteInventoryById("123");
        });
    }
    @Test
    public void testDeleteInventoryByIdBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Inventory.deleteInventoryById("   ");
        });
    }
    @Test
    public void testFindInventoryByProductId() {
        assertDoesNotThrow(() -> {
            InputValidator.Inventory.findInventoryByProductId("123");
        });
    }
    @Test
    public void testFindInventoryByProductIdBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Inventory.findInventoryByProductId("   ");
        });
    }
    @Test
    public void testCreateSaleOrder() {
        assertDoesNotThrow(() -> {
            InputValidator.Orders.createSaleOrder(TestUtil.OrderTestData.Order1Request);
        });
    }
    @Test
    public void testCreateSaleOrderBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Orders.createSaleOrder(
                    new ProductsCatalog(List.of(
                            new ProductsCatalog.ProductData("   ", 123, 12, 12)
                    ))
                    );
        });
    }
    @Test
    public void testCreatePurchaseOrder() {
        assertDoesNotThrow(() -> {
            InputValidator.Orders.createPurchaseOrder(TestUtil.OrderTestData.Order1Request);
        });
    }
    @Test
    public void testCreatePurchaseOrderBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Orders.createPurchaseOrder(
                    new ProductsCatalog(List.of(
                            new ProductsCatalog.ProductData("1123", -1, 12, 12)
                    ))
            );
        });
    }
    @Test
    public void testFindOrderById() {
        assertDoesNotThrow(() -> {
            InputValidator.Orders.findOrderById("123");
        });
    }
    @Test
    public void testFindOrderByIdBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Orders.findOrderById("   ");
        });
    }
    @Test
    public void testFindOrderByProductId() {
        assertDoesNotThrow(() -> {
            InputValidator.Orders.findOrderByProductId("123");
        });
    }
    @Test
    public void testFindOrderByProductIdBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Orders.findOrderByProductId("   ");
        });
    }
    @Test
    public void testDeleteOrderById() {
        assertDoesNotThrow(() -> {
            InputValidator.Orders.deleteOrderById("123");
        });
    }
    @Test
    public void testDeleteOrderByIdBlankError() {
        assertThrows(CustomException.ValidationException.class, () -> {
            InputValidator.Orders.deleteOrderById("   ");
        });
    }
}
