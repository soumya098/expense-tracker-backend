package com.soumya.expense_tracker_backend.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.soumya.expense_tracker_backend.dto.ApiError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles validation exceptions, returns a 400 response with an appropriate
   * error message.
   * 
   * @param ex      the validation exception
   * @param request the current HTTP request
   * @return a ResponseEntity containing an ApiError object with a 400 status code
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex,
      HttpServletRequest request) {

    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(err -> err.getField() + ": " + err.getDefaultMessage()).findFirst().orElse("Validation Error");

    ApiError error = new ApiError(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        message,
        request.getRequestURI());

    return ResponseEntity.badRequest().body(error);
  }

  /**
   * Handles ResourceNotFoundException, returns a 404 response with an appropriate
   * error message.
   * 
   * @param ex      the ResourceNotFoundException
   * @param request the current HTTP request
   * @return a ResponseEntity containing an ApiError object with a 404 status code
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

    ApiError error = new ApiError(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "Not Found",
        ex.getMessage(),
        request.getRequestURI());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  /**
   * Handles IllegalArgumentException, returns a 400 response with an appropriate
   * error message.
   *
   * @param ex      the IllegalArgumentException
   * @param request the current HTTP request
   * @return a ResponseEntity containing an ApiError object with a 400 status code
   */

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {

    ApiError error = new ApiError(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        ex.getMessage(),
        request.getRequestURI());

    return ResponseEntity.badRequest().body(error);
  }

  /**
   * Handles AuthenticationException, returns a 401 response with an appropriate
   * error message.
   *
   * @param ex      the AuthenticationException
   * @param request the current HTTP request
   * @return a ResponseEntity containing an ApiError object with a 401 status code
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiError> handleAuth(AuthenticationException ex, HttpServletRequest request) {

    ApiError error = new ApiError(
        LocalDateTime.now(),
        HttpStatus.UNAUTHORIZED.value(),
        "Unauthorized",
        ex.getMessage(),
        request.getRequestURI());

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  /**
   * Handles UserAlreadyExistsException, returns a 409 response with an
   * appropriate
   * error message.
   *
   * @param ex      the UserAlreadyExistsException
   * @param request the current HTTP request
   * @return a ResponseEntity containing an ApiError object with a 409 status code
   */
  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ApiError> handleUserExists(UserAlreadyExistsException ex, HttpServletRequest request) {

    ApiError error = new ApiError(
        LocalDateTime.now(),
        HttpStatus.CONFLICT.value(),
        "User Already Exists",
        ex.getMessage(),
        request.getRequestURI());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  /**
   * Handles HttpMessageNotReadableException, which is thrown when an invalid
   * value is provided for an enum field.
   * Returns a 400 response with an appropriate error message.
   * 
   * @param ex      the HttpMessageNotReadableException
   * @param request the current HTTP request
   * @return a ResponseEntity containing an ApiError object with a 400 status code
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiError> handleInvalidEnum(HttpMessageNotReadableException ex, HttpServletRequest request) {

    ApiError error = new ApiError(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        "Invalid value for enum field",
        request.getRequestURI());

    return ResponseEntity.badRequest().body(error);
  }

  /**
   * Handles any unexpected exceptions, returns a 500 response with an appropriate
   * error message.
   *
   * @param ex      the unexpected exception
   * @param request the current HTTP request
   * @return a ResponseEntity containing an ApiError object with a 500 status code
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {

    ApiError error = new ApiError(
        LocalDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        "An unexpected error occurred",
        request.getRequestURI());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
