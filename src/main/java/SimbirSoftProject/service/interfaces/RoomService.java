package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.domain.dto.RoomDto;
import SimbirSoftProject.domain.util.Room;

import java.util.List;

public interface RoomService {
    String createRoom(RoomDto roomDto);
    Room getRoomById(Long id);
    List<Room> getAllRooms();
    String getRoomType(Room room);
    String deleteRoomById(Long id);
}
