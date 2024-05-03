package ua.com.example.exception;

public class BirthDateRequiredException extends RuntimeException {

    public BirthDateRequiredException() {
        super("Birth date is required");
    }
}
