package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class Item {
    private int id;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotBlank(message = "Описание должно быть заполнено")
    private String description;
    @NotNull(message = "Необходимо указать доступность вещи")
    private Boolean available;
    private int ownerId;
    private int request;
}
