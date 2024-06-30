package ru.practicum.shareit.user;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.user.UserDto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        return mapper.entityToDto(userRepository.save(mapper.dtoToEntity(userDto)));
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
