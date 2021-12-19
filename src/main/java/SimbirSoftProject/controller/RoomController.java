package SimbirSoftProject.controller;

import SimbirSoftProject.controller.dto.RoomDto;
import SimbirSoftProject.entity.Room;
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.roomService.createRoom(roomDto);
        return new ResponseEntity<>(roomDto,HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RoomDto roomDto = this.roomService.getRoomById(id);
        if (roomDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(roomDto);
    }
    @GetMapping("/get/all")
    public ResponseEntity<List<RoomDto>> getAllRooms(){
        List<RoomDto> roomDtoList = this.roomService.getAllRooms();

        if(roomDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(roomDtoList,HttpStatus.OK);
    }
    @GetMapping("/get/type")
    public ResponseEntity<String> getRoomType(@PathVariable Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RoomDto roomDto = this.roomService.getRoomById(id);
        if (roomDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String type = this.roomService.getRoomType(id);
        return new ResponseEntity<>(type,HttpStatus.OK);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RoomDto> deleteRoomById(@PathVariable Long id){
        RoomDto roomDto = this.roomService.getRoomById(id);
        if (roomDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.roomService.deleteRoomById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
