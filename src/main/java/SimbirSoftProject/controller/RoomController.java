package SimbirSoftProject.controller;

import SimbirSoftProject.controller.dto.RoomDto;
import SimbirSoftProject.entity.Room;
import SimbirSoftProject.service.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestBody RoomDto roomDto){
        roomService.createRoom(roomDto);
        return ResponseEntity.ok("Room created successfully");
    }
    @GetMapping("/get/{id}")
    public Room getRoomById(@PathVariable Long id){
        return roomService.getRoomById(id);
    }
    @GetMapping("/get/all")
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }
    @GetMapping("/get/type")
    public String getRoomType(@PathVariable Long id){
        return roomService.getRoomType(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoomById(@PathVariable Long id){
        roomService.deleteRoomById(id);
        return ResponseEntity.ok("Room deleted successfully");
    }
}
