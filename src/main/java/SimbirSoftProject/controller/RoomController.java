package SimbirSoftProject.controller;

import SimbirSoftProject.domain.dto.RoomDto;
import SimbirSoftProject.domain.util.Room;
import SimbirSoftProject.service.interfaces.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;
    @Autowired
    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }
    @PostMapping("/create")
    public String createRoom(@RequestBody RoomDto roomDto){
        roomService.createRoom(roomDto);
        return "Room created successfully";
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
    public String getRoomType(@RequestBody Room room){
        return roomService.getRoomType(room);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteRoomById(@PathVariable Long id){
        roomService.deleteRoomById(id);
        return "Room deleted successfully";
    }
}
