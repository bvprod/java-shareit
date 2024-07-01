package ru.practicum.shareit.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String message;

    @JsonCreator
    public ErrorResponse(@JsonProperty("error") String message) {
        this.message = message;
    }
}
