package SimbirSoftProject.service.implement;

import SimbirSoftProject.controller.dto.RoomDto;
import SimbirSoftProject.controller.dto.UserDto;
import SimbirSoftProject.entity.Room;
import SimbirSoftProject.entity.User;
import SimbirSoftProject.mapper.RoomMapper;
import SimbirSoftProject.mapper.UserMapper;
import SimbirSoftProject.repository.RoomRepository;
import SimbirSoftProject.service.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    @Override
    public void createRoom(RoomDto roomDto) {
        roomRepository.save(roomDto.roomDtoToRoom(roomDto));
    }

    @Override
    public RoomDto getRoomById(Long id) {
        return roomToRoomDto(roomRepository.getById(id));
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return allToDTO(roomRepository.findAll());
    }

    @Override
    public String getRoomType(Long id) {
        Room room1 = roomRepository.getById(id);
        return room1.getType().toString();
    }

    @Override
    public void deleteRoomById(Long id) {
        roomRepository.deleteById(id);
    }
    private RoomDto roomToRoomDto(Room room){
        return RoomMapper.ROOM_MAPPER.roomToRoomDto(room);
    }
    private List<RoomDto> allToDTO(List<Room> roomList){
        return RoomMapper.ROOM_MAPPER.allToDTO(roomList);
    }
}
