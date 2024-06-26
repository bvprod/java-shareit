package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotBlank(message = "Описание должно быть заполнено")
    private String description;
    @NotNull(message = "Необходимо указать доступность вещи")
    private Boolean available;
    private Long ownerId;
    private Long request;
}
