package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptionHandler.exceptions.EmailNotValidException;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectAlreadyExistsException;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectNotValidException;

import java.util.*;

@Repository
@Slf4j
public class UserRepositoryInMemoryImpl implements UserRepository {

    private final Map<Integer, User> users = new HashMap<>();

    private int id = 1;

    @Override
    public User createUser(User user) {
        if (users.values().stream()
                .noneMatch(user1 -> Objects.equals(user1.getEmail(), user.getEmail()))) {
            if (user.getId() == null) {
                user.setId(id++);
                users.put(user.getId(), user);
            } else {
                throw new ObjectNotValidException("При создании нового пользователя поле id должно отсутствовать");
            }
        } else {
            throw new ObjectAlreadyExistsException("Такой пользователь уже существует");
        }
        log.info(String.format("Создан пользователь %s, id=%d", user.getName(), user.getId()));
        return user;
    }

    @Override
    public User updateUser(int userId, User newUser) {
        User user = getUser(userId);
        if (newUser.getName() != null && !newUser.getName().isBlank()) {
            user.setName(newUser.getName());
        }
        if (newUser.getEmail() != null && !newUser.getEmail().isBlank()) {
            if (EmailValidator.getInstance().isValid(newUser.getEmail())) {
                if (users.values().stream()
                        .filter(u -> u.getId() != userId)
                        .anyMatch(u -> u.getEmail().equals(newUser.getEmail()))) {
                    throw new ObjectAlreadyExistsException("Пользователь с таким email уже существует");
                } else {
                    user.setEmail(newUser.getEmail());
                }
            } else {
                throw new EmailNotValidException("Неверный формат email");
            }
        }
        users.put(userId, user);
        log.info("Обновлена информация о пользователе c id=" + userId);
        return user;
    }

    @Override
    public User getUser(int userId) {
        if (users.containsKey(userId)) {
            log.info("Запрошена информация о пользователе с id=" + userId);
            return users.get(userId);
        } else {
            throw new ObjectDoesNotExistException("Такого пользователя не существует");
        }
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Запрошен список всех пользователей");
        return new ArrayList<>(users.values());
    }

    @Override
    public User deleteUser(int userId) {
        if (users.containsKey(userId)) {
            log.info("Удален пользователь c id=" + userId);
            return users.remove(userId);
        } else {
            throw new ObjectDoesNotExistException("Такого пользователя не существует");
        }
    }
}
