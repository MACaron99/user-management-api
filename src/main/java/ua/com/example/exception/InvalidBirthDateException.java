package ua.com.example.exception;

public class InvalidBirthDateException extends RuntimeException {

    public InvalidBirthDateException() {
        super("Invalid birth date. It must be earlier than current date");
    }
}
