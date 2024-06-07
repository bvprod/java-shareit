package ru.practicum.shareit.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
public class User {
    private int id;
    private String name;
    @NotBlank(message = "Поле email не должно быть пустым")
    @Email(message = "Поле email должно быть заполнено как адрес электронной почты")
    private String email;

    public User(int id, String name, String email) {
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
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
