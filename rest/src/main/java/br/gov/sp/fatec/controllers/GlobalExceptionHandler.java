package br.gov.sp.fatec.controllers;

import br.gov.sp.fatec.dtos.ErrorResponseDto;
import br.gov.sp.fatec.exceptions.PersonNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(PersonNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handlePersonNotFoundException(
      PersonNotFoundException ex,
      HttpServletRequest request) {

    ErrorResponseDto error = new ErrorResponseDto(
        request.getRequestURI(),
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        ex.getMessage());

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex,
      HttpServletRequest request) {

    String message = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));

    ErrorResponseDto error = new ErrorResponseDto(
        request.getRequestURI(),
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        message);

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(error);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(
      ConstraintViolationException ex,
      HttpServletRequest request) {

    String message = ex.getConstraintViolations()
        .stream()
        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
        .collect(Collectors.joining(", "));

    ErrorResponseDto error = new ErrorResponseDto(
        request.getRequestURI(),
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        message);

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleGenericException(
      Exception ex,
      HttpServletRequest request) {

    System.err.println("Internal server error: " + ex.getClass().getName());
    ex.printStackTrace();

    ErrorResponseDto error = new ErrorResponseDto(
        request.getRequestURI(),
        LocalDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "An internal server error occurred");

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(error);
  }
}
