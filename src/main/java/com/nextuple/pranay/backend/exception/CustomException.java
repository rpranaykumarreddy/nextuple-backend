package com.nextuple.pranay.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Product Not Found",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SaveNotSuccessfulException.class)
    public ResponseEntity<?> handleSaveNotSuccessfulException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Product Not Saved",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<?> handleInventoryNotFoundException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Inventory Not Found",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(QuantityNotAvailableException.class)
    public ResponseEntity<?> handleQuantityNotAvailableException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Quantity Not Available",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(Exception ex, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails("Order Not Found",ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    public static class SaveNotSuccessfulException extends RuntimeException {
        public SaveNotSuccessfulException(String message) {
            super(message);
        }
    }
    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

    public static class InventoryNotFoundException extends RuntimeException {
        public InventoryNotFoundException(String message) {
            super(message);
        }
    }

    public static class QuantityNotAvailableException extends RuntimeException{
        public QuantityNotAvailableException(String message) {
            super(message
            );
        }
    }public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
            super(message);
        }
    }

}
