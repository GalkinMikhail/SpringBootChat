package SimbirSoftProject.controller;


import SimbirSoftProject.controller.dto.UserDto;
import SimbirSoftProject.entity.User;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        return ResponseEntity.ok("User created success");
    }
    @GetMapping("/get/All")
    public List<User> getAll(){
        return userService.getAll();
    }
    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
