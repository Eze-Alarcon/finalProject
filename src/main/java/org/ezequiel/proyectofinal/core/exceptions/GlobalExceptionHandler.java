package org.ezequiel.proyectofinal.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Se usa cuando se lanza la excepción de recurso no encontrado. */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Resource Not Found");
        problem.setType(URI.create("https://api.proyectofinal.ezequiel.org/errors/not-found"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    /** Se usa cuando se lanza la excepción de validación de argumentos no válidos. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors);
        problem.setTitle("Validation Failed");
        problem.setType(URI.create("https://api.proyectofinal.ezequiel.org/errors/validation"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    /** Se usa cuando se lanza la excepción de cualquier otro tipo de error no previsto. */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred: " + ex.getMessage()
        );
        problem.setTitle("Internal Server Error");
        problem.setType(URI.create("https://api.proyectofinal.ezequiel.org/errors/internal"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}
