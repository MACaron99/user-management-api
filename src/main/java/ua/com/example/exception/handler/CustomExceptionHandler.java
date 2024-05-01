package ua.com.example.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.com.example.exception.*;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidBirthDateException.class)
    public ResponseEntity<Object> handleInvalidBirthDateException(InvalidBirthDateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleInvalidEmailException(InvalidEmailException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAgeInsufficientException.class)
    public ResponseEntity<Object> handleUserAgeInsufficientException(UserAgeInsufficientException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidDateRangeException.class})
    public ResponseEntity<Object> handleInvalidDateRangeException(InvalidDateRangeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
