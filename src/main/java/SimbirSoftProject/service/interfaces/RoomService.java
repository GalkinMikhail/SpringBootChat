package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.dto.RoomDto;
import SimbirSoftProject.dto.RoomViewDto;
import SimbirSoftProject.dto.UserToAddDto;
import SimbirSoftProject.model.User;

import java.util.List;

public interface RoomService {
    void createRoom(RoomDto roomDto, User user);
    RoomViewDto getRoomById(Long id);
    List<RoomViewDto> getAllRooms();
    String getRoomType(Long id);
    void deleteRoomById(Long id);
    void addParticipant(UserToAddDto userToAddDto, Long id);
    void deleteParticipant(UserToAddDto userToAddDto, Long id);
    void renameRoom(RoomDto roomDto, Long id);
}
