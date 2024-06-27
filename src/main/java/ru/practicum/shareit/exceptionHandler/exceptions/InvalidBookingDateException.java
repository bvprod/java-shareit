package ru.practicum.shareit.exceptionHandler.exceptions;

public class InvalidBookingDateException extends RuntimeException{
    public InvalidBookingDateException(String message) {
        super(message);
    }
}
