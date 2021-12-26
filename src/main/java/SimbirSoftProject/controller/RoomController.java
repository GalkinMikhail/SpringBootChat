package SimbirSoftProject.controller;

import SimbirSoftProject.dto.RoomDto;
import SimbirSoftProject.exceptions.ResourceNotFoundException;
import SimbirSoftProject.service.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    @PostMapping("/create")
    public ResponseEntity<RoomDto> createRoom(@RequestBody @Valid RoomDto roomDto){
        if (roomDto == null){
            throw new ResourceNotFoundException("Failed to create the room", "room name,room type");
        }
        this.roomService.createRoom(roomDto);
        return new ResponseEntity<>(roomDto,HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id){
        RoomDto roomDto = this.roomService.getRoomById(id);
        if (roomDto == null){
            throw new ResourceNotFoundException("Room with id " + id + "not found", "room id");
        }
        return ResponseEntity.ok(roomDto);
    }
    @GetMapping("/get/all")
    public ResponseEntity<List<RoomDto>> getAllRooms(){
        List<RoomDto> roomDtoList = this.roomService.getAllRooms();

        if(roomDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(roomDtoList,HttpStatus.OK);
    }
    @GetMapping("/get/type/{id}")
    public ResponseEntity<String> getRoomType(@PathVariable Long id){
        RoomDto roomDto = this.roomService.getRoomById(id);
        if (roomDto == null){
            throw new ResourceNotFoundException("Room with id " + id + "not found", "room id");
        }
        String type = this.roomService.getRoomType(id);
        return new ResponseEntity<>(type,HttpStatus.OK);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RoomDto> deleteRoomById(@PathVariable Long id){
        RoomDto roomDto = this.roomService.getRoomById(id);
        if (roomDto == null){
            throw new ResourceNotFoundException("Room with id " + id + "not found", "room id");
        }
        this.roomService.deleteRoomById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
