package SimbirSoftProject.controller;


import SimbirSoftProject.controller.dto.UserDto;
import SimbirSoftProject.entity.User;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        if (userDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.userService.createUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @GetMapping("/get/All")
    public ResponseEntity<List<UserDto>> getAll(){
        List<UserDto> userDtoList = this.userService.getAll();

        if (userDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userDtoList,HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = this.userService.getUserById(id);
        if (userDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable Long id){
        UserDto userDto = this.userService.getUserById(id);
        if (userDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
