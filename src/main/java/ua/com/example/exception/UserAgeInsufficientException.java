package ua.com.example.exception;

public class UserAgeInsufficientException extends RuntimeException {

    public UserAgeInsufficientException() {
        super("Insufficient age. User must be at least 18 years old");
    }
}
