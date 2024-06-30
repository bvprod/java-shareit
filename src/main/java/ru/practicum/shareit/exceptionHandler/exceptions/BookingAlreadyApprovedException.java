package ru.practicum.shareit.exceptionHandler.exceptions;

public class BookingAlreadyApprovedException extends RuntimeException {
    public BookingAlreadyApprovedException(String message) {
        super(message);
    }
}
