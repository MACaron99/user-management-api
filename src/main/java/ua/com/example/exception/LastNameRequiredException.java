package ua.com.example.exception;

public class LastNameRequiredException extends RuntimeException {

    public LastNameRequiredException() {
        super("Last name is required");
    }
}
