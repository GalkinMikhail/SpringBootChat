package SimbirSoftProject.controller;


import SimbirSoftProject.dto.RegistrationDto;
import SimbirSoftProject.dto.UserBlockDto;
import SimbirSoftProject.dto.UserDto;
import SimbirSoftProject.exceptions.ResourceNotFoundException;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity<RegistrationDto> registration(@RequestBody RegistrationDto registrationDto){
        this.userService.registration(registrationDto);
        return new ResponseEntity<>(registrationDto, HttpStatus.CREATED);
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
    @PreAuthorize("hasAuthority('ROLE_MODERATOR') OR hasAuthority('ROLE_ADMIN')")
    @PostMapping("/block/{id}")
    public ResponseEntity<String> blockUser(@RequestBody UserBlockDto userBlockDto, @PathVariable Long id){
        UserDto userDto = this.userService.getUserById(id);
        if (userDto == null){
            throw new ResourceNotFoundException("User with id " + id + "not found", "user id");
        }
        this.userService.blockUser(id,userBlockDto);
        return new ResponseEntity<>("User successfully blocked", HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_MODERATOR') OR hasAuthority('ROLE_ADMIN')")
    @PostMapping("/unblock/{id}")
    public ResponseEntity<String> unblockUser(@PathVariable Long id) {
        UserDto userDto = this.userService.getUserById(id);
        if (userDto == null){
            throw new ResourceNotFoundException("User with id " + id + "not found", "user id");
        }
        this.userService.unBlockUser(id);
        return new ResponseEntity<>("User successfully unblocked", HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/mod/{id}")
    public ResponseEntity<String> setModerator(@PathVariable Long id) {
        UserDto userDto = this.userService.getUserById(id);
        if (userDto == null){
            throw new ResourceNotFoundException("User with id " + id + "not found", "user id");
        }
        this.userService.setModerator(id);
        String login = userDto.getLogin();
        return new ResponseEntity<>( login + " is now moderator", HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/unmod/{id}")
    public ResponseEntity<String> deleteModerator(@PathVariable Long id) {
        UserDto userDto = this.userService.getUserById(id);
        if (userDto == null){
            throw new ResourceNotFoundException("User with id " + id + "not found", "user id");
        }
        this.userService.deleteModerator(id);
        String login = userDto.getLogin();
        return new ResponseEntity<>( login + " is now not moderator", HttpStatus.OK);
    }

}
