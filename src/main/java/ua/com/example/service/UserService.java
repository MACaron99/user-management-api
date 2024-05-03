package ua.com.example.service;

import ua.com.example.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    void createUser(UserDto userDto);
    void deleteUser(Long id);
    void updateAllUserFields(Long id, UserDto userDto);
    void updateSomeUserFields(Long id, UserDto userDto);
    List<UserDto> getUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate);
}
