package SimbirSoftProject.controller;


import SimbirSoftProject.domain.dto.UserDto;
import SimbirSoftProject.domain.util.User;
import SimbirSoftProject.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        return "User created success";
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
    public String deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return "User deleted successfully";
    }

}
