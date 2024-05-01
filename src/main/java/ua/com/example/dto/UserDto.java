package ua.com.example.dto;

import lombok.Getter;
import lombok.Setter;
import ua.com.example.entity.User;

import java.util.Date;

@Getter
@Setter
public class UserDto {

    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String phoneNumber;

    public UserDto(User user) {
        setEmail(user.getEmail());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setBirthDate(user.getBirthDate());
        setAddress(user.getAddress());
        setPhoneNumber(user.getPhoneNumber());
    }
}
