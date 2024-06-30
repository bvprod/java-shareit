package ru.practicum.shareit.exceptionHandler.exceptions;

public class CommentForbiddenException extends RuntimeException {
    public CommentForbiddenException(String message) {
        super(message);
    }
}
