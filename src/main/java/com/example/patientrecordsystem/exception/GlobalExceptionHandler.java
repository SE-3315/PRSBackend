package com.example.patientrecordsystem.exception;

import com.example.patientrecordsystem.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

/**
 * Global exception handler for REST controllers.
 * 
 * <p>Handles exceptions thrown by controllers and services, returning
 * appropriate HTTP status codes and error messages.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles NotFoundException (404 Not Found).
     *
     * @param ex the NotFoundException
     * @return error response with 404 status
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        logger.warn("Resource not found: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles IllegalArgumentException (400 Bad Request).
     *
     * @param ex the IllegalArgumentException
     * @return error response with 400 status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("Invalid argument: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles DataIntegrityViolationException (409 Conflict or 400 Bad Request).
     * 
     * <p>This typically occurs when database constraints are violated,
     * such as foreign key constraints or unique constraints.
     *
     * @param ex the DataIntegrityViolationException
     * @return error response with appropriate status
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        logger.error("Data integrity violation: {}", ex.getMessage(), ex);
        
        String message = "Data integrity violation";
        String details = ex.getMessage();
        
        // Try to extract more meaningful error message
        if (ex.getCause() != null) {
            details = ex.getCause().getMessage();
            if (details != null && details.contains("foreign key")) {
                message = "Cannot delete: This record is referenced by other records";
            } else if (details != null && details.contains("unique constraint")) {
                message = "Duplicate entry: This value already exists";
            }
        }
        
        ErrorResponse error = new ErrorResponse(message, details);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Handles all other exceptions (500 Internal Server Error).
     *
     * @param ex the exception
     * @return error response with 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
                "An unexpected error occurred",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

