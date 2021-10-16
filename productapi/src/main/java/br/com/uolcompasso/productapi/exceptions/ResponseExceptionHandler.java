package br.com.uolcompasso.productapi.exceptions;

import br.com.uolcompasso.productapi.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import javax.el.MethodNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handleArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(400, "Bad Request: Field cannot be null, empty or negative.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(400, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(400, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(MethodNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodNotFoundExceptions(MethodNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodNotAllowedExceptions(MethodNotAllowedException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(405, ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(exceptionResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodNotSupportedExceptions(HttpRequestMethodNotSupportedException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(405, ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(exceptionResponse);
    }

}
