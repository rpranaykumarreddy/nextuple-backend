package com.nextuple.pranay.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler {
    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Validation Error",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Product Not Found",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    public static class SaveNotSuccessfulException extends RuntimeException {
        public SaveNotSuccessfulException(String message) {
            super(message);
        }
    }
    @ExceptionHandler(SaveNotSuccessfulException.class)
    public ResponseEntity<?> handleSaveNotSuccessfulException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Product Not Saved",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    public static class InventoryNotFoundException extends RuntimeException {
        public InventoryNotFoundException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<?> handleInventoryNotFoundException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Inventory Not Found",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    public static class QuantityNotAvailableException extends RuntimeException{
        public QuantityNotAvailableException(String message) {
            super(message
            );
        }
    }
    @ExceptionHandler(QuantityNotAvailableException.class)
    public ResponseEntity<?> handleQuantityNotAvailableException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Quantity Not Available",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
            super(message);
        }
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Order Not Found",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    public static class InventoryAlreadyExistsException extends RuntimeException {
        public InventoryAlreadyExistsException(String message) {
            super(message);
        }
    }
    @ExceptionHandler(InventoryAlreadyExistsException.class)
    public ResponseEntity<?> handleInventoryAlreadyExistsException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Inventory Already Exists",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    public static class ProductCatalogNotFoundExpception extends RuntimeException{
        public ProductCatalogNotFoundExpception(String message) {
            super(message);
        }
    }
    @ExceptionHandler(ProductCatalogNotFoundExpception.class)
    public ResponseEntity<?> handleProductCatalogNotFoundExpception(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Product Catalog Not Found",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    public static class QuantityIsZeroOrNegativeException extends RuntimeException {
        public QuantityIsZeroOrNegativeException(String message) {
            super(message);
        }
    }
    @ExceptionHandler(QuantityIsZeroOrNegativeException.class)
    public ResponseEntity<?> handleQuantityIsZeroOrNegativeException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Quantity is Zero or Negative",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
