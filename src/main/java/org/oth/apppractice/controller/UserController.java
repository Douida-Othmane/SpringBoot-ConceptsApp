package org.oth.apppractice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.oth.apppractice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.oth.apppractice.dto.UserDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/api/v1/User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> userDtos = userService.getUsers();
        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable @Min(1) Long userId){
        UserDto userDto = userService.findById(userId);
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){
        userService.saveUser(userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String email) {
        userService.updateUser(userId, name, email);
        return ResponseEntity.ok().build();
    }
}
