package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserDto.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public UserDto updateUser(int userId, UserDto userDto) {
        return userRepository.updateUser(userId, userDto);
    }

    @Override
    public UserDto getUser(int userId) {
        return userRepository.getUser(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserDto deleteUser(int userId) {
        return userRepository.deleteUser(userId);
    }
}
