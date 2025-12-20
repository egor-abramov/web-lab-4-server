package server.config;

import io.lettuce.core.RedisCommandTimeoutException;
import jakarta.xml.bind.ValidationException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import server.dto.response.ErrorResponse;
import server.exception.InvalidTokenException;
import server.exception.UserAlreadyExistsException;
import server.exception.UserNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(ValidationException e) {
        return baseHandler(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return baseHandler(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException e) {
        return baseHandler(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException e) {
        return baseHandler(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ErrorResponse errors = new ErrorResponse();

        e.getBindingResult().getFieldErrors().forEach(error -> errors.addError(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler({RedisConnectionFailureException.class, RedisCommandTimeoutException.class})
    public ResponseEntity<ErrorResponse> handleRedisException(RedisConnectionFailureException e) {
        return baseHandler("Authentication storage is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<ErrorResponse> baseHandler(
            String message, HttpStatus status) {
        ErrorResponse errors = new ErrorResponse();
        errors.addError("error", message);
        return ResponseEntity.status(status).body(errors);
    }
}