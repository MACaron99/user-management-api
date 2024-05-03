package ua.com.example.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.com.example.dto.UserDto;
import ua.com.example.exception.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class ValidationUtil {

    private static int minUserAge;

    public ValidationUtil(@Value("${user.min.age}") int minUserAge) {
        ValidationUtil.minUserAge = minUserAge;
    }

    public static void validateBirthDate(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new InvalidBirthDateException();
        } else if (Period.between(birthDate, LocalDate.now()).getYears() < minUserAge) {
            throw new UserAgeInsufficientException();
        }
    }

    public static void validateDateRange(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate) || fromDate.isEqual(toDate)) {
            throw new InvalidDateRangeException();
        }
    }

    public static void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException();
        }
    }

    public static void validateRequiredFields(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new EmailRequiredException();
        } else if (userDto.getFirstName() == null || userDto.getFirstName().isBlank()) {
            throw new FirstNameRequiredException();
        } else if (userDto.getLastName() == null || userDto.getLastName().isBlank()) {
            throw new LastNameRequiredException();
        } else if (userDto.getBirthDate() == null) {
            throw new BirthDateRequiredException();
        }
    }
}
