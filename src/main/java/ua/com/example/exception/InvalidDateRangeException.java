package ua.com.example.exception;

public class InvalidDateRangeException extends RuntimeException {

    public InvalidDateRangeException() {
        super("Invalid date range. 'From' date must be less than 'To' date.");
    }
}
