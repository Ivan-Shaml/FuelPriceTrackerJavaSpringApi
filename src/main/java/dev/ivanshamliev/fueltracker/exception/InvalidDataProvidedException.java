package dev.ivanshamliev.fueltracker.exception;

public class InvalidDataProvidedException extends RuntimeException{
    public InvalidDataProvidedException(String message) {
        super(message);
    }
}
