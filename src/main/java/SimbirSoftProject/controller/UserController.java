package SimbirSoftProject.controller;


import SimbirSoftProject.dto.UserDto;
import SimbirSoftProject.exceptions.ResourceNotFoundException;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody @Valid UserDto userDto){
        this.userService.registration(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @GetMapping("/get/All")
    public ResponseEntity<List<UserDto>> getAll(){
        List<UserDto> userDtoList = this.userService.getAll();

        if (userDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userDtoList,HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        UserDto userDto = this.userService.getUserById(id);
        if (userDto == null){
            throw new ResourceNotFoundException("User with id " + id + "not found", "user id");
        }
        return ResponseEntity.ok(userDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable Long id){
        UserDto userDto = this.userService.getUserById(id);
        if (userDto == null){
            throw new ResourceNotFoundException("User with id " + id + "not found", "user id");
        }

        this.userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
