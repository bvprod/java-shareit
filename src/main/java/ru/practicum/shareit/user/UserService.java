package ru.practicum.shareit.user;

import ru.practicum.shareit.user.UserDto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(int userId, UserDto userDto);

    UserDto getUser(int userId);

    List<UserDto> getAllUsers();

    UserDto deleteUser(int userId);
}
