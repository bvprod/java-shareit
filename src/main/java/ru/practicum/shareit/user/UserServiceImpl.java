package ru.practicum.shareit.user;

import com.sun.jdi.ObjectCollectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.user.UserDto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapperInterface mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapperInterface mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return mapper.entityToDto(userRepository.save(mapper.dtoToEntity(userDto)));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ObjectDoesNotExistException("Данного пользователя не существует"));
        mapper.updateUserFromDto(userDto, user);
        return mapper.entityToDto(userRepository.save(user));
    }

    @Override
    public UserDto getUser(Long userId) {
        return mapper.entityToDto(userRepository.findById(userId).orElseThrow(() ->
                new ObjectDoesNotExistException("Данного пользователя не существует")
        ));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(mapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
