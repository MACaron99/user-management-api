package ua.com.example.service;

import ua.com.example.dto.UserDto;

import java.util.Date;
import java.util.List;

public interface UserService {

    void createUser(UserDto userDto);
    void deleteUser(String id);
    void updateAllUserFields(String id, UserDto userDto);
    void updateSomeUserFields(String id, UserDto userDto);
    List<UserDto> getUsersByBirthDateRange(Date fromDate, Date toDate);
}
