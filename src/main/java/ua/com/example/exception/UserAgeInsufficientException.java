package ua.com.example.exception;

public class UserAgeInsufficientException extends RuntimeException {

    public UserAgeInsufficientException(String message) {
        super(message);
    }
}
