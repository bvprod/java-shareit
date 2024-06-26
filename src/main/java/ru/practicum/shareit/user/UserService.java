package ru.practicum.shareit.user;

import ru.practicum.shareit.user.UserDto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);

    UserDto getUser(Long userId);

    List<UserDto> getAllUsers();

    void deleteUser(Long userId);
}
