package chat.service.interfaces;

import chat.dto.RoomDto;
import chat.dto.RoomRenameDto;
import chat.dto.RoomViewDto;
import chat.dto.UserToAddDto;
import chat.model.User;

import java.util.List;

public interface RoomService {
    void createRoom(RoomDto roomDto, User user);
    RoomViewDto getRoomById(Long id);
    List<RoomViewDto> getAllRooms();
    String getRoomType(Long id);
    void deleteRoomById(Long id);
    void addParticipant(UserToAddDto userToAddDto, Long id);
    void deleteParticipant(UserToAddDto userToAddDto, Long id);
    void renameRoom(RoomRenameDto roomRenameDto, Long id);
}
