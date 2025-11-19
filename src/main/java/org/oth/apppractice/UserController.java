package org.oth.apppractice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.oth.apppractice.DTO.UserDTO;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<UserDTO> userDTOs = userService.getUsers();
        return ResponseEntity.ok().body(userDTOs);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable @Min(1) Long userId){
        UserDTO userDTO = userService.findById(userId);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
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
