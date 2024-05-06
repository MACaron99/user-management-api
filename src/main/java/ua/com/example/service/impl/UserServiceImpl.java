package ua.com.example.service.impl;

import org.springframework.stereotype.Service;
import ua.com.example.dto.UserDto;
import ua.com.example.entity.User;
import ua.com.example.exception.*;
import ua.com.example.service.UserService;
import ua.com.example.util.IdGenerationUtil;
import ua.com.example.util.ValidationUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> users;

    public UserServiceImpl(List<User> users) {
        this.users = users;
    }

    @Override
    public void createUser(UserDto userDto)  {
        ValidationUtil.validateRequiredFields(userDto);
        ValidationUtil.validateBirthDate(userDto.getBirthDate());
        ValidationUtil.validateEmail(userDto.getEmail());

        User user = new User(userDto);
        user.setId(IdGenerationUtil.generateId());
        users.add(user);
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(findUserById(id));
    }

    @Override
    public void updateAllUserFields(Long id, UserDto userDto) {
        User user = findUserById(id);

        if (userDto.getBirthDate() != null) {
            ValidationUtil.validateBirthDate(userDto.getBirthDate());
        }
        if (userDto.getEmail() != null) {
            ValidationUtil.validateEmail(userDto.getEmail());
        }

        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthDate(userDto.getBirthDate());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
    }

    @Override
    public void updateSomeUserFields(Long id, UserDto userDto) {
        User user = findUserById(id);

        if (userDto.getEmail() != null) {
            ValidationUtil.validateEmail(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }

        if (userDto.getBirthDate() != null) {
            ValidationUtil.validateBirthDate(userDto.getBirthDate());
            user.setBirthDate(userDto.getBirthDate());
        }

        if (userDto.getAddress() != null) {
            user.setAddress(userDto.getAddress());
        }

        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
    }

    @Override
    public List<UserDto> getUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate) {
        ValidationUtil.validateDateRange(fromDate, toDate);

        List<UserDto> usersInRange = new ArrayList<>();
        for (User user : users) {
            if (!user.getBirthDate().isBefore(fromDate) && !user.getBirthDate().isAfter(toDate)) {
                usersInRange.add(new UserDto(user));
            }
        }
        return usersInRange;
    }

    public User findUserById(Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }
}

