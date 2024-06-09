package ru.practicum.shareit.user.UserDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    @NotBlank(message = "Поле email не должно быть пустым")
    @Email(message = "Поле email должно быть заполнено как адрес электронной почты")
    private String email;

    public UserDto(Integer id, String name, String email) {
        this.id = id;
        if (name.isBlank()) {
            this.name = email;
        } else {
            this.name = name;
        }
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
