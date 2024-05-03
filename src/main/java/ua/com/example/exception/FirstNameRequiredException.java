package ua.com.example.exception;

public class FirstNameRequiredException extends RuntimeException {

    public FirstNameRequiredException() {
        super("First name is required");
    }
}
