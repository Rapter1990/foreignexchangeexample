package com.example.demo.exception.handler;

import com.example.demo.exception.ExchangeNotFoundException;
import com.example.demo.exception.error.CustomError;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler named {@link GlobalExceptionHandler} for handling various types of exceptions in the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles MethodArgumentNotValidException thrown when validation on an argument annotated with @Valid fails.
     *
     * @param ex The MethodArgumentNotValidException instance.
     * @return ResponseEntity with CustomError containing details of validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {

        List<CustomError.CustomSubError> subErrors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    if (error instanceof FieldError fieldError) {
                        String fieldName = fieldError.getField();
                        String message = fieldError.getDefaultMessage();
                        subErrors.add(
                                CustomError.CustomSubError.builder()
                                        .field(fieldName)
                                        .message(message)
                                        .build()
                        );
                    } else if (error != null) {
                        String objectName = error.getObjectName();
                        String message = error.getDefaultMessage();
                        subErrors.add(
                                CustomError.CustomSubError.builder()
                                        .field(objectName)
                                        .message(message)
                                        .build()
                        );
                    }
                }
        );

        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.VALIDATION_ERROR.getName())
                .message("Validation failed")
                .subErrors(subErrors)
                .build();

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handles ConstraintViolationException thrown when a bean validation constraint is violated.
     *
     * @param constraintViolationException The ConstraintViolationException instance.
     * @return ResponseEntity with CustomError containing details of constraint violations.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handlePathVariableErrors(final ConstraintViolationException constraintViolationException) {

        List<CustomError.CustomSubError> subErrors = new ArrayList<>();
        constraintViolationException.getConstraintViolations()
                .forEach(constraintViolation ->
                        subErrors.add(
                                CustomError.CustomSubError.builder()
                                        .message(constraintViolation.getMessage())
                                        .field(StringUtils.substringAfterLast(constraintViolation.getPropertyPath().toString(), "."))
                                        .value(constraintViolation.getInvalidValue() != null ? constraintViolation.getInvalidValue().toString() : null)
                                        .type(constraintViolation.getInvalidValue().getClass().getSimpleName())
                                        .build()
                        )
                );

        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.VALIDATION_ERROR.getName())
                .message("Constraint violation")
                .subErrors(subErrors)
                .build();

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handles RuntimeException thrown for general runtime exceptions.
     *
     * @param runtimeException The RuntimeException instance.
     * @return ResponseEntity with CustomError containing details of the runtime exception.
     */
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> handleRuntimeException(final RuntimeException runtimeException) {
        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .header(CustomError.Header.API_ERROR.getName())
                .message(runtimeException.getMessage())
                .build();

        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles ExchangeNotFoundException thrown when an exchange is not found.
     *
     * @param ex The ExchangeNotFoundException instance.
     * @return ResponseEntity with CustomError containing details of the exception.
     */
    @ExceptionHandler(ExchangeNotFoundException.class)
    protected ResponseEntity<CustomError> handleExchangeNotFoundException(final ExchangeNotFoundException ex) {
        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .header(CustomError.Header.NOT_FOUND.getName())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }

}
