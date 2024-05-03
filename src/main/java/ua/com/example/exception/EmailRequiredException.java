package ua.com.example.exception;

public class EmailRequiredException extends RuntimeException {

    public EmailRequiredException() {
        super("Email is required");
    }
}
