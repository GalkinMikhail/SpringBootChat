package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.controller.dto.RoomDto;
import SimbirSoftProject.entity.Room;

import java.util.List;

public interface RoomService {
    String createRoom(RoomDto roomDto);
    Room getRoomById(Long id);
    List<Room> getAllRooms();
    String getRoomType(Long id);
    String deleteRoomById(Long id);
}
