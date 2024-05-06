package ua.com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.example.dto.UserDto;
import ua.com.example.entity.User;
import ua.com.example.exception.*;
import ua.com.example.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private User user;

    private List<User> users;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        userService = new UserServiceImpl(users);
        user = initializeUser();
    }

    private User initializeUser() {
        User user = new User(1L, "example@example.com", "Henry", "Correy",
                LocalDate.of(2000, 1, 1));
        users.add(user);
        return user;
    }

    @Test
    void createUser_ValidUserDto_ShouldAddUserToList() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setFirstName("Artem");
        userDto.setLastName("Anderson");
        userDto.setBirthDate(LocalDate.of(1999, 5, 17));

        userService.createUser(userDto);

        assertTrue(users.contains(new User(userDto)));
    }

    @Test
    void createUser_WithEmptyEmail_ShouldThrowEmailRequiredException() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("Artem");
        userDto.setLastName("Anderson");
        userDto.setBirthDate(LocalDate.of(1999, 5, 17));

        assertThrows(EmailRequiredException.class, () -> userService.createUser(userDto));
    }

    @Test
    void createUser_WithEmptyFirstName_ShouldThrowFirstNameRequiredException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setLastName("Anderson1");
        userDto.setBirthDate(LocalDate.of(1999, 5, 17));

       assertThrows(FirstNameRequiredException.class, () -> userService.createUser(userDto));
    }

    @Test
    void createUser_WithEmptyLastName_ShouldThrowLastNameRequiredException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setFirstName("Artem");
        userDto.setBirthDate(LocalDate.of(1999, 5, 17));

        assertThrows(LastNameRequiredException.class, () -> userService.createUser(userDto));
    }

    @Test
    void createUser_WithEmptyBirthDate_ShouldThrowBirthDateRequiredException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setFirstName("Artem");
        userDto.setLastName("Anderson");

        assertThrows(BirthDateRequiredException.class, () -> userService.createUser(userDto));
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

    @Test
    void updateSomeUserFields_WithUpdatedEmail_ShouldUpdateEmailField() {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setEmail("example@email.com");

        userService.updateSomeUserFields(1L, updatedUserDto);

        User updatedUser = userService.findUserById(1L);
        assertEquals(updatedUserDto.getEmail(), updatedUser.getEmail());
    }

    @Test
    void updateSomeUserFields_WithUpdatedFirstName_ShouldUpdateFirstNameField() {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setFirstName("Artem");

        userService.updateSomeUserFields(1L, updatedUserDto);

        User updatedUser = userService.findUserById(1L);
        assertEquals(updatedUserDto.getFirstName(), updatedUser.getFirstName());
    }

    @Test
    void updateSomeUserFields_WithUpdatedLastName_ShouldUpdateLastNameField() {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setLastName("Anderson");

        userService.updateSomeUserFields(1L, updatedUserDto);

        User updatedUser = userService.findUserById(1L);
        assertEquals(updatedUserDto.getLastName(), updatedUser.getLastName());
    }

    @Test
    void updateSomeUserFields_WithUpdatedBirthDate_ShouldUpdateBirthDateField() {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setBirthDate(LocalDate.of(1999, 5, 17));

        userService.updateSomeUserFields(1L, updatedUserDto);

        User updatedUser = userService.findUserById(1L);
        assertEquals(updatedUserDto.getBirthDate(), updatedUser.getBirthDate());
    }

    @Test
    void updateSomeUserFields_WithUpdatedAddress_ShouldUpdateAddressField() {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setAddress("Ukraine Street 3");

        userService.updateSomeUserFields(1L, updatedUserDto);

        User updatedUser = userService.findUserById(1L);
        assertEquals(updatedUserDto.getAddress(), updatedUser.getAddress());
    }

    @Test
    void updateSomeUserFields_WithUpdatedPhoneNumber_ShouldUpdatePhoneNumberField() {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setPhoneNumber("+380666666666");

        userService.updateSomeUserFields(1L, updatedUserDto);

        User updatedUser = userService.findUserById(1L);
        assertEquals(updatedUserDto.getPhoneNumber(), updatedUser.getPhoneNumber());
    }

    @Test
    void updateSomeUserFields_WithInvalidEmailFormat_ShouldThrowInvalidEmailException() {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setEmail("invalid_email");

        assertThrows(InvalidEmailException.class, () -> userService.updateSomeUserFields(1L, updatedUserDto));
    }

    @Test
    void updateSomeUserFields_WithInvalidBirthDate_ShouldThrowInvalidBirthDateException() {
        UserDto invalidUserDto = new UserDto();
        invalidUserDto.setBirthDate(LocalDate.of(2025, 1, 1));

        assertThrows(InvalidBirthDateException.class, () -> userService.updateSomeUserFields(1L, invalidUserDto));
    }

    @Test
    void updateSomeUserFields_WithInsufficientAge_ShouldThrowUserAgeInsufficientException() {
        UserDto invalidUserDto = new UserDto();
        invalidUserDto.setBirthDate(LocalDate.of(2015, 1, 1));

        assertThrows(UserAgeInsufficientException.class, () -> userService.updateSomeUserFields(1L, invalidUserDto));
    }

    @Test
    void updateAllUserFields_AllFieldsUpdated_Successfully() {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setEmail("updated_email@email.com");
        updatedUserDto.setFirstName("UpdatedFirstName");
        updatedUserDto.setLastName("UpdatedLastName");
        updatedUserDto.setBirthDate(LocalDate.of(1990, 5, 17));

        userService.updateAllUserFields(1L, updatedUserDto);

        User updatedUser = userService.findUserById(1L);
        assertEquals(updatedUserDto.getEmail(), updatedUser.getEmail());
        assertEquals(updatedUserDto.getFirstName(), updatedUser.getFirstName());
        assertEquals(updatedUserDto.getLastName(), updatedUser.getLastName());
        assertEquals(updatedUserDto.getBirthDate(), updatedUser.getBirthDate());
        assertEquals(updatedUserDto.getAddress(), updatedUser.getAddress());
        assertEquals(updatedUserDto.getPhoneNumber(), updatedUser.getPhoneNumber());
    }

    @Test
    void updateAllUserFields_WithInvalidEmailFormat_ShouldThrowInvalidEmailException() {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setEmail("invalid_email");

        assertThrows(InvalidEmailException.class, () -> userService.updateAllUserFields(1L, updatedUserDto));
    }

    @Test
    void updateAllUserFields_WithInvalidBirthDate_ShouldThrowInvalidBirthDateException() {
        UserDto invalidUserDto = new UserDto();
        invalidUserDto.setBirthDate(LocalDate.of(2025, 1, 1));

        assertThrows(InvalidBirthDateException.class, () -> userService.updateAllUserFields(1L, invalidUserDto));
    }

    @Test
    void updateAllUserFields_WithInsufficientAge_ShouldThrowUserAgeInsufficientException() {
        UserDto invalidUserDto = new UserDto();
        invalidUserDto.setBirthDate(LocalDate.of(2015, 1, 1));

        assertThrows(UserAgeInsufficientException.class, () -> userService.updateAllUserFields(1L, invalidUserDto));
    }

    @Test
    void deleteUser_WithValidId_ShouldDeleteUser() {
        assertTrue(users.contains(user));

        userService.deleteUser(1L);
        assertFalse(users.contains(user));
    }

    @Test
    void deleteUser_WithInvalidId_ShouldThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99L));
    }

    @Test
    void getUsersByBirthDateRange_WithValidDateRange_ShouldReturnUsersInDateRange() {
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(1999, 12, 31);

        List<UserDto> expectedUsers = new ArrayList<>();

        expectedUsers.add(new UserDto(new User(1L,"john@email.com", "John", "Willard",
                LocalDate.of(1995, 5, 10))));
        expectedUsers.add(new UserDto(new User(2L, "deodat@email.com", "Samuel", "Lawson",
                LocalDate.of(1990, 8, 15))));
        expectedUsers.add(new UserDto(new User(3L, "alice@email.com", "Alice", "Johnson",
                LocalDate.of(1998, 3, 20))));

        users.add(new User(2L, "john@email.com", "John", "Willard",
                LocalDate.of(1995, 5, 10)));
        users.add(new User(3L, "deodat@email.com", "Samuel", "Lawson",
                LocalDate.of(1990, 8, 15)));
        users.add(new User(4L, "alice@email.com", "Alice", "Johnson",
                LocalDate.of(1998, 3, 20)));
        users.add(new User(5L, "bob@email.com", "Bob", "Brown",
                LocalDate.of(1985, 12, 5)));

        List<UserDto> actualUsers = userService.getUsersByBirthDateRange(fromDate, toDate);

        assertEquals(expectedUsers.size(), actualUsers.size());
        assertTrue(actualUsers.containsAll(expectedUsers));
    }

    @Test
    void getUsersByBirthDateRange_WithInvalidDateRange_ShouldThrowInvalidDateRangeException() {
        LocalDate fromDate = LocalDate.of(2000, 1, 1);
        LocalDate toDate = LocalDate.of(1999, 12, 31);

        assertThrows(InvalidDateRangeException.class, () -> userService.getUsersByBirthDateRange(fromDate, toDate));
    }

    @Test
    void getUsersByBirthDateRange_WithEmptyDateRange_ShouldReturnEmptyList() {
        LocalDate fromDate = LocalDate.of(2022, 1, 1);
        LocalDate toDate = LocalDate.of(2022, 12, 31);

        List<UserDto> usersInRange = userService.getUsersByBirthDateRange(fromDate, toDate);
        assertTrue(usersInRange.isEmpty());
    }

    @Test
    void getUsersByBirthDateRange_WithEmptyUserList_ShouldReturnEmptyList() {
        users.clear();

        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 12, 31);

        List<UserDto> actualUsers = userService.getUsersByBirthDateRange(fromDate, toDate);

        assertTrue(actualUsers.isEmpty());
    }
}
