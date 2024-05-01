package ua.com.example.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.com.example.dto.UserDto;
import ua.com.example.entity.User;
import ua.com.example.exception.*;
import ua.com.example.service.UserService;
import ua.com.example.util.IdGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> users;
    private final int minUserAge;

    public UserServiceImpl(@Value("${user.min.age}") int minUserAge) {
        this.users = new ArrayList<>();
        this.minUserAge = minUserAge;
    }

    @Override
    public void createUser(UserDto userDto)  {
        if (isBirthDateValid(userDto.getBirthDate())) {
            if (isUserOldEnough(userDto.getBirthDate())) {
                if (isEmailValid(userDto.getEmail())) {
                    User user = new User(userDto);
                    user.setId(IdGenerator.generateId());
                    users.add(user);
                } else {
                    throw new InvalidEmailException("Entered email is not valid");
                }
            } else {
                throw new UserAgeInsufficientException("User must be at least 18 years old");
            }
        } else {
            throw new InvalidBirthDateException("Birth date must be earlier than current date");
        }
    }

    @Override
    public void deleteUser(String id) {
        users.remove(findUserById(Long.valueOf(id)));
    }

    @Override
    public void updateAllUserFields(String id, UserDto userDto) {
        User user = findUserById(Long.valueOf(id));

        if (isBirthDateValid(userDto.getBirthDate())) {
            if (isUserOldEnough(userDto.getBirthDate())) {
                if (isEmailValid(userDto.getEmail())) {
                    user.setEmail(user.getEmail());
                    user.setFirstName(userDto.getFirstName());
                    user.setLastName(userDto.getLastName());
                    user.setBirthDate(userDto.getBirthDate());
                    user.setAddress(userDto.getAddress());
                    user.setPhoneNumber(userDto.getPhoneNumber());
                } else throw new InvalidEmailException("Entered email is not valid");
            } else {
                throw new UserAgeInsufficientException("User must be at least 18 years old");
            }
        } else {
            throw new InvalidBirthDateException("Birth date must be earlier than current date");
        }
    }

    @Override
    public void updateSomeUserFields(String id, UserDto userDto) {
        User user = findUserById(Long.valueOf(id));

        if (userDto.getEmail() != null) {
            if (isEmailValid(userDto.getEmail())) {
                user.setEmail(userDto.getEmail());
            } else {
                throw new InvalidEmailException("Entered email is not valid");
            }
        }

        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            user.setLastName(user.getLastName());
        }

        if (userDto.getBirthDate() != null) {
            if (isBirthDateValid(userDto.getBirthDate())) {
                if (isUserOldEnough(userDto.getBirthDate())) {
                    user.setBirthDate(userDto.getBirthDate());
                } else {
                    throw new UserAgeInsufficientException("User must be at least 18 years old");
                }
            } else {
                throw new InvalidBirthDateException("Birth date must be earlier than current date");
            }
        }

        if (userDto.getAddress() != null) {
            user.setAddress(userDto.getAddress());
        }

        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
    }

    @Override
    public List<UserDto> getUsersByBirthDateRange(Date fromDate, Date toDate) {
        if (fromDate.compareTo(toDate) >= 0) {
            throw new InvalidDateRangeException("Invalid date range. 'From' date must be less than 'To' date.");
        }

        List<UserDto> usersInRange = new ArrayList<>();
        for (User user : users) {
            if (user.getBirthDate().after(fromDate) && user.getBirthDate().before(toDate)) {
                usersInRange.add(new UserDto(user));
            }
        }
        return usersInRange;
    }

    private User findUserById(Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException("User with id {" + id + "} not found");
    }

    private boolean isUserOldEnough(Date birthDate) {
        long ageInMillis = new Date().getTime() - birthDate.getTime();
        int ageInYears = (int) (ageInMillis / (1000L * 60 * 60 * 24 * 365));
        return ageInYears >= minUserAge;
    }

    private boolean isBirthDateValid(Date birthDate) {
        return birthDate.before(new Date());
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

