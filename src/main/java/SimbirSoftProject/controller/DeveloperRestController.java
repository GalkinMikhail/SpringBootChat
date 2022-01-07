package SimbirSoftProject.controller;

import SimbirSoftProject.dto.UserDto;
import SimbirSoftProject.exceptions.ResourceNotFoundException;
import SimbirSoftProject.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class DeveloperRestController {


    private final UserService userService;

    @Autowired
    public DeveloperRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        try {
            UserDto result = userService.getUserById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e){
                throw new ResourceNotFoundException("User with id " + id + " not found", "user id");
        }
    }
}
