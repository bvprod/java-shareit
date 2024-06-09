package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserDto.UserDto;
import ru.practicum.shareit.user.UserDto.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        return UserMapper.userToDto(userRepository.createUser(UserMapper.dtoToUser(userDto)));
    }

    @Override
    public UserDto updateUser(int userId, UserDto userDto) {
        return UserMapper.userToDto(userRepository.updateUser(userId, UserMapper.dtoToUser(userDto)));
    }

    @Override
    public UserDto getUser(int userId) {
        return UserMapper.userToDto(userRepository.getUser(userId));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream().map(UserMapper::userToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto deleteUser(int userId) {
        return UserMapper.userToDto(userRepository.deleteUser(userId));
    }
}
