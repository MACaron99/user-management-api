package ua.com.example.entity;

import lombok.Getter;
import lombok.Setter;
import ua.com.example.dto.UserDto;

import java.util.Date;

@Getter
@Setter
public class User {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String phoneNumber;

    public User(UserDto userDto) {
        setEmail(userDto.getEmail());
        setFirstName(userDto.getFirstName());
        setLastName(userDto.getLastName());
        setBirthDate(userDto.getBirthDate());
        setAddress(userDto.getAddress());
        setPhoneNumber(userDto.getPhoneNumber());
    }
}
