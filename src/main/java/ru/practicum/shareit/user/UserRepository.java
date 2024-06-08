package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {
    User createUser(User user);

    User updateUser(int userId, User newUser);

    User getUser(int userId);

    List<User> getAllUsers();

    User deleteUser(int userId);
}
