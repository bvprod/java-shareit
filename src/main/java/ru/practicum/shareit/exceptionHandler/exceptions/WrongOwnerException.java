package ru.practicum.shareit.exceptionHandler.exceptions;

public class WrongOwnerException extends RuntimeException {
    public WrongOwnerException(String message) {
        super(message);
    }
}
