package ua.com.example.exception;

public class InvalidDateRangeException extends RuntimeException {

    public InvalidDateRangeException(String message) {
        super(message);
    }
}
