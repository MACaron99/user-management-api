package ua.com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.com.example.dto.UserDto;
import ua.com.example.entity.User;
import ua.com.example.exception.InvalidBirthDateException;
import ua.com.example.exception.InvalidEmailException;
import ua.com.example.exception.UserAgeInsufficientException;
import ua.com.example.util.IdGenerator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    public User[] users = new User[10];
    public int userNumber = 0;
    private final int minUserAge;

    public UserService(@Value("${user.min.age}") int minUserAge) {
        this.minUserAge = minUserAge;
    }

    public void createUser(UserDto userDto)  {
        if (userNumber == users.length - 1) {
            User[] newUsers = new User[users.length * 2];
            System.arraycopy(users, 0, newUsers, 0, users.length);
            users = newUsers;
            addUser(userDto);
        } else {
            addUser(userDto);
        }
    }

    private void addUser(UserDto userDto) {
        if (isBirthDateValid(userDto.getBirthDate())) {
            if (isUserOldEnough(userDto.getBirthDate())) {
                if (isEmailValid(userDto.getEmail())) {
                    User user = new User(userDto);
                    user.setId(IdGenerator.generateId());
                    users[userNumber] = user;
                    userNumber++;
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

    public void deleteUser(UserDto userDto) {

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

