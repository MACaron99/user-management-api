package ua.com.example.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException() {
        super("Entered email is not valid");
    }
}
