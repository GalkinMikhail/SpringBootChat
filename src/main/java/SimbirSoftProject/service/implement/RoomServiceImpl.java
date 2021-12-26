package SimbirSoftProject.service.implement;

import SimbirSoftProject.dto.RoomDto;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.RoomType;
import SimbirSoftProject.mapper.RoomMapper;
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
        roomDto.setName(roomDto.getName());
        roomDto.setType(RoomType.PUBLIC);
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
