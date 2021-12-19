package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.controller.dto.RoomDto;
import SimbirSoftProject.entity.Room;

import java.util.List;

public interface RoomService {
    void createRoom(RoomDto roomDto);
    RoomDto getRoomById(Long id);
    List<RoomDto> getAllRooms();
    String getRoomType(Long id);
    void deleteRoomById(Long id);
}
