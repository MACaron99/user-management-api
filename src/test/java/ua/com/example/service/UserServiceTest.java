package ua.com.example.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.example.dto.UserDto;
import ua.com.example.entity.User;
import ua.com.example.exception.*;
import ua.com.example.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private List<User> users;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(users);
    }

    @Test
    void createUser_ValidUserDto_ShouldAddUserToList() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setFirstName("Artem");
        userDto.setLastName("Anderson");
        userDto.setBirthDate(LocalDate.of(1999, 5, 17));

        userService.createUser(userDto);

        verify(users).add(any(User.class));
    }

    @Test
    void createUser_WithEmptyEmail_ShouldThrowEmailRequiredException() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("Artem");
        userDto.setLastName("Anderson");
        userDto.setBirthDate(LocalDate.of(1999, 5, 17));

        Assertions.assertThrows(EmailRequiredException.class, () -> userService.createUser(userDto));
    }

    @Test
    void createUser_WithEmptyFirstName_ShouldThrowFirstNameRequiredException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setLastName("Anderson1");
        userDto.setBirthDate(LocalDate.of(1999, 5, 17));

        Assertions.assertThrows(FirstNameRequiredException.class, () -> userService.createUser(userDto));
    }

    @Test
    void createUser_WithEmptyLastName_ShouldThrowLastNameRequiredException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setFirstName("Artem");
        userDto.setBirthDate(LocalDate.of(1999, 5, 17));

        Assertions.assertThrows(LastNameRequiredException.class, () -> userService.createUser(userDto));
    }

    @Test
    void createUser_WithEmptyBirthDate_ShouldThrowBirthDateRequiredException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setFirstName("Artem");
        userDto.setLastName("Anderson");

        Assertions.assertThrows(BirthDateRequiredException.class, () -> userService.createUser(userDto));
    }

    @Test
    void createUser_WithInvalidBirthDate_ShouldThrowInvalidBirthDateException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setFirstName("Artem");
        userDto.setLastName("Anderson");
        userDto.setBirthDate(LocalDate.of(2029, 5, 17));

        assertThrows(InvalidBirthDateException.class, () -> userService.createUser(userDto));
    }

    @Test
    void createUser_WithInsufficientAge_ShouldThrowUserAgeInsufficientException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setFirstName("Artem");
        userDto.setLastName("Anderson");
        userDto.setBirthDate(LocalDate.of(2009, 5, 17));

        assertThrows(UserAgeInsufficientException.class, () -> userService.createUser(userDto));
    }

    @Test
    void createUser_WithInvalidEmail_ShouldThrowInvalidEmailException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("invalid_email");
        userDto.setFirstName("Artem");
        userDto.setLastName("Anderson");
        userDto.setBirthDate(LocalDate.of(1999, 5, 17));

        assertThrows(InvalidEmailException.class, () -> userService.createUser(userDto));
    }


}
