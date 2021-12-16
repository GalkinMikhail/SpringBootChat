package SimbirSoftProject.service.implement;

import SimbirSoftProject.controller.dto.RoomDto;
import SimbirSoftProject.entity.Room;
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
    public String createRoom(RoomDto roomDto) {
        roomRepository.save(roomDto.roomDtoToRoom(roomDto));
        return "Room created successfully";
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.getById(id);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public String getRoomType(Long id) {
        Room room1 = roomRepository.getById(id);
        return room1.getType().toString();
    }

    @Override
    public String deleteRoomById(Long id) {
        roomRepository.deleteById(id);
        return "Room deleted successfully";
    }
}
