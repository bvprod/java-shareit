package ru.practicum.shareit.exceptionHandler.exceptions;

public class ObjectDoesNotExistException extends RuntimeException {
    public ObjectDoesNotExistException(String message) {
        super(message);
    }
}
