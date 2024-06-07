package ru.practicum.shareit.user;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptionHandler.exceptions.EmailNotValidException;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectAlreadyExistsException;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.user.UserDto.UserDto;
import ru.practicum.shareit.user.UserDto.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryInMemoryImpl implements UserRepository {

    private final Map<Integer, User> users = new HashMap<>();

    private int id = 1;

    @Override
    public UserDto createUser(User user) {
        if (!users.containsValue(user)) {
            user.setId(id++);
            users.put(user.getId(), user);
        } else {
            throw new ObjectAlreadyExistsException("Такой пользователь уже существует");
        }
        return UserMapper.userToDto(user);
    }

    @Override
    public UserDto updateUser(int userId, UserDto userDto) {
        User user = UserMapper.dtoToUser(getUser(userId));
        if (userDto.getName() != null && !userDto.getName().isBlank()) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            if (EmailValidator.getInstance().isValid(userDto.getEmail())) {
                if (users.values().stream()
                        .filter(u -> u.getId() != userId)
                        .anyMatch(u -> u.getEmail().equals(userDto.getEmail()))) {
                    throw new ObjectAlreadyExistsException("Пользователь с таким email уже существует");
                } else {
                    user.setEmail(userDto.getEmail());
                }
            } else {
                throw new EmailNotValidException("Неверный формат email");
            }
        }
        users.put(userId, user);
        return UserMapper.userToDto(user);
    }

    @Override
    public UserDto getUser(int userId) {
        if (users.containsKey(userId)) {
            return UserMapper.userToDto(users.get(userId));
        } else {
            throw new ObjectDoesNotExistException("Такого пользователя не существует");
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        return users.values().stream().map(UserMapper::userToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto deleteUser(int userId) {
        if (users.containsKey(userId)) {
            return UserMapper.userToDto(users.remove(userId));
        } else {
            throw new ObjectDoesNotExistException("Такого пользователя не существует");
        }
    }
}
