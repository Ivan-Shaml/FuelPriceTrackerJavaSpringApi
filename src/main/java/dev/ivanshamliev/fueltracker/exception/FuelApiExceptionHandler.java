package dev.ivanshamliev.fueltracker.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@ControllerAdvice @Slf4j
public class FuelApiExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException (EntityNotFoundException ex) {
        var httpStatus = HttpStatus.NOT_FOUND;

        var apiException = new ApiExceptionResponse(
                ex.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        log.error(ex.getMessage());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {DuplicateEntityException.class})
    public ResponseEntity<Object> handleDuplicateEntityException (DuplicateEntityException ex) {
        var httpStatus = HttpStatus.CONFLICT;

        var apiException = new ApiExceptionResponse(
                ex.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        log.error(ex.getMessage());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {InvalidDataProvidedException.class})
    public ResponseEntity<Object> handleInvalidDataProvidedException (InvalidDataProvidedException ex) {
        var httpStatus = HttpStatus.BAD_REQUEST;

        var apiException = new ApiExceptionResponse(
                ex.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        log.error(ex.getMessage());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {DateTimeParseException.class})
    public ResponseEntity<Object> handleDateTimeParseException (DateTimeParseException ex) {
        var httpStatus = HttpStatus.BAD_REQUEST;

        var apiException = new ApiExceptionResponse(
                ex.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        log.error(ex.getMessage());
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
