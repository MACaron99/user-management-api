package ua.com.example.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.example.dto.UserDto;
import ua.com.example.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        try {
            userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateSomeUserFields(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.updateSomeUserFields(id, userDto);
        return ResponseEntity.ok("User fields updated successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAllUserFields(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.updateAllUserFields(id, userDto);
        return ResponseEntity.ok("User fields updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        List<UserDto> usersInRange = userService.getUsersByBirthDateRange(fromDate, toDate);
        return ResponseEntity.ok(usersInRange);
    }
}
