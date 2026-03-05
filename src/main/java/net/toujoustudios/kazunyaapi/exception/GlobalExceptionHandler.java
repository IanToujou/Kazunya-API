package net.toujoustudios.kazunyaapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for this application.
 *
 * @since 1.0.0
 * @author Toujou Studios
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors related to arguments.
     *
     * @param exception The actual exception thrown.
     * @return A ResponseEntity with a bad request status and the error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleValidationError(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
