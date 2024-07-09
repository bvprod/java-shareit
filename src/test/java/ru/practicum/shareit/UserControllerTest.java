package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserDto.UserDto;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @MockBean
    private UserService userService;

    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private MockMvc mvc;

    private final UserDto userDto = new UserDto(1L, "name", "email@email.ru");

    @Test
    void createUserTest() {
        when(userService.createUser(any())).thenReturn(userDto);
    }

}
