package SimbirSoftProject.controller;

import SimbirSoftProject.dto.RoomDto;
import SimbirSoftProject.dto.RoomRenameDto;
import SimbirSoftProject.dto.RoomViewDto;
import SimbirSoftProject.dto.UserToAddDto;
import SimbirSoftProject.exceptions.ResourceNotFoundException;
import SimbirSoftProject.exceptions.UserBlockedException;
import SimbirSoftProject.model.Role;
import SimbirSoftProject.model.User;
import SimbirSoftProject.security.SecurityUser;
import SimbirSoftProject.service.interfaces.RoomService;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final UserService userService;
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomDto){
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        User user = userService.findByLogin(login);
        if (roomDto == null){
            throw new ResourceNotFoundException("Failed to create the room", "room name,room type");
        }
        this.roomService.createRoom(roomDto, user);
        return new ResponseEntity<>(roomDto,HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<RoomViewDto> getRoomById(@PathVariable Long id){
        RoomViewDto roomViewDto = this.roomService.getRoomById(id);
        if (roomViewDto == null){
            throw new ResourceNotFoundException("Room with id " + id + "not found", "room id");
        }
        return ResponseEntity.ok(roomViewDto);
    }
    @GetMapping("/get/all")
    public ResponseEntity<List<RoomViewDto>> getAllRooms(){
        List<RoomViewDto> roomDtoList = this.roomService.getAllRooms();

        if(roomDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(roomDtoList,HttpStatus.OK);
    }
    @GetMapping("/get/type/{id}")
    public ResponseEntity<String> getRoomType(@PathVariable Long id){
        RoomViewDto roomViewDto = this.roomService.getRoomById(id);
        if (roomViewDto == null){
            throw new ResourceNotFoundException("Room with id " + id + "not found", "room id");
        }
        String type = this.roomService.getRoomType(id);
        return new ResponseEntity<>(type,HttpStatus.OK);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RoomDto> deleteRoomById(@PathVariable Long id){
        RoomViewDto roomViewDto = this.roomService.getRoomById(id);
        if (roomViewDto == null){
            throw new ResourceNotFoundException("Room with id " + id + "not found", "room id");
        }
        this.roomService.deleteRoomById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/add/{id}")
    public ResponseEntity<RoomViewDto> addParticipant(@RequestBody UserToAddDto userToAddDto, @PathVariable Long id){
        RoomViewDto roomViewDto = this.roomService.getRoomById(id);
        if (roomViewDto == null){
            throw new ResourceNotFoundException("Room with id " + id + "not found", "room id");
        }
        this.roomService.addParticipant(userToAddDto,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/kick/{id}")
    public ResponseEntity<RoomViewDto> deleteParticipant(@RequestBody UserToAddDto userToAddDto, @PathVariable Long id){
        RoomViewDto roomViewDto = this.roomService.getRoomById(id);
        if (roomViewDto == null){
            throw new ResourceNotFoundException("Room with id " + id + "not found", "room id");
        }
        this.roomService.deleteParticipant(userToAddDto,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/rename/{id}")
    public ResponseEntity<String> renameRoom(@RequestBody RoomRenameDto roomRenameDto, @PathVariable Long id){
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        User user = userService.findByLogin(login);
        boolean UserBlocked = user.isBlocked();
        if (UserBlocked){
            throw new UserBlockedException("User " + user.getLogin() + " is blocked","user blocked");
        }
        RoomViewDto roomViewDto = this.roomService.getRoomById(id);
        if (roomViewDto == null){
            throw new ResourceNotFoundException("Room with id " + id + "not found", "room id");
        }
        boolean isAdmin = false;
        Set<Role> userRoles = user.getRoles();
        for (Role role : userRoles){
            if (role.getName().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (!user.getLogin().equals(roomViewDto.getOwner().getLogin()) && !isAdmin){
            throw new AccessDeniedException("You have no permission to rename this room");
        }
        this.roomService.renameRoom(roomRenameDto,id);
        return new ResponseEntity<>("Room renamed successfully", HttpStatus.OK);
    }
}
