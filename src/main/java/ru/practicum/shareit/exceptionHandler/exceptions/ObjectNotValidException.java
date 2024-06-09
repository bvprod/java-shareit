package ru.practicum.shareit.exceptionHandler.exceptions;

public class ObjectNotValidException extends RuntimeException {
    public ObjectNotValidException(String message) {
        super(message);
    }
}
