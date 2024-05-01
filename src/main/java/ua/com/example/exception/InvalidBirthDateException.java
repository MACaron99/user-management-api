package ua.com.example.exception;

public class InvalidBirthDateException extends RuntimeException {

    public InvalidBirthDateException(String message) {
        super(message);
    }
}
