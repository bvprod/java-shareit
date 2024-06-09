package ru.practicum.shareit.user.UserDto;

import ru.practicum.shareit.user.User;

public class UserMapper {
    public static UserDto userToDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User dtoToUser(UserDto user) {
        return new User(user.getId(), user.getName(), user.getEmail());
    }

}
