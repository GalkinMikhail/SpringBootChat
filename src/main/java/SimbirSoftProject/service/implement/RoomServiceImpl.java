package SimbirSoftProject.service.implement;

import SimbirSoftProject.dto.RoomDto;
import SimbirSoftProject.dto.RoomViewDto;
import SimbirSoftProject.dto.UserToAddDto;
import SimbirSoftProject.exceptions.ResourceNotFoundException;
import SimbirSoftProject.exceptions.UserBlockedException;
import SimbirSoftProject.mapper.RoomMapper;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.User;
import SimbirSoftProject.repository.RoomRepository;
import SimbirSoftProject.repository.UserRepository;
import SimbirSoftProject.service.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    @Override
    public void createRoom(RoomDto roomDto, User user) {
        boolean isUserBlocked = user.isBlocked();
        if (isUserBlocked && !user.getBlockDate().plusMinutes(user.getBlockingDurationInMinutes()).isBefore(LocalDateTime.now())){
            throw new UserBlockedException("User " + user.getLogin() + " is blocked","user blocked");
        }
        Room roomToCreate = new Room();
        roomToCreate.setName(roomDto.getName());
        roomToCreate.setType(roomDto.getType());
        roomToCreate.setCreatorId(user);
        Set<User> addCreatorToRoom = new HashSet<>();
        addCreatorToRoom.add(user);
        roomToCreate.setParticipants(addCreatorToRoom);
        roomRepository.save(roomToCreate);
    }

    @Override
    public RoomViewDto getRoomById(Long id) {
        return roomToRoomViewDto(roomRepository.getById(id));
    }

    @Override
    public List<RoomViewDto> getAllRooms() {
        return allRoomsToRoomsViewDto(roomRepository.findAll());
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

    @Override
    public void addParticipant(UserToAddDto userToAddDto, Long id) {
        User user = userRepository.findByLogin(userToAddDto.getLogin());
        if (user == null){
            throw new ResourceNotFoundException("User with login" + userToAddDto.getLogin() + "not found", "user login");
        }
        boolean UserBlocked = user.isBlocked();
        if (UserBlocked && !user.getBlockDate().plusMinutes(user.getBlockingDurationInMinutes()).isBefore(LocalDateTime.now())){
            throw new UserBlockedException("User " + user.getLogin() + " is blocked","user blocked");
        }
        Room room = roomRepository.getById(id);
        room.getParticipants().add(user);
        roomRepository.save(room);
    }

    @Override
    public void deleteParticipant(UserToAddDto userToAddDto, Long id) {
        User user = userRepository.findByLogin(userToAddDto.getLogin());
        if (user == null){
            throw new ResourceNotFoundException("User with login" + userToAddDto.getLogin() + "not found", "user login");
        }
        boolean UserBlocked = user.isBlocked();
        if (UserBlocked && !user.getBlockDate().plusMinutes(user.getBlockingDurationInMinutes()).isBefore(LocalDateTime.now())){
            throw new UserBlockedException("User " + user.getLogin() + " is blocked","user blocked");
        }
        Room room = roomRepository.getById(id);
        Set<User> listParticipants = room.getParticipants();
        if (!listParticipants.contains(user)){
            throw new ResourceNotFoundException("User is not a member of this room", "room participants");
        }
        listParticipants.remove(user);
        room.setParticipants(listParticipants);
        roomRepository.save(room);
    }

    @Override
    public void renameRoom(RoomDto roomDto, Long id) {
        Room room = roomRepository.getById(id);
        room.setName(roomDto.getName());
        roomRepository.save(room);
    }

    private RoomDto roomToRoomDto(Room room){
        return RoomMapper.ROOM_MAPPER.roomToRoomDto(room);
    }
    private List<RoomDto> allToDTO(List<Room> roomList){
        return RoomMapper.ROOM_MAPPER.allToDTO(roomList);
    }
    public RoomViewDto roomToRoomViewDto(Room room){
        RoomViewDto roomViewDto = new RoomViewDto();
        roomViewDto.setName(room.getName());
        roomViewDto.setOwner(room.getCreatorId());
        roomViewDto.setType(room.getType());
        roomViewDto.setParticipants(room.getParticipants());
        return roomViewDto;
    }
    public List<RoomViewDto> allRoomsToRoomsViewDto(List<Room> roomList){
        List<RoomViewDto> list = new ArrayList<>(roomList.size());
        for ( Room room : roomList ) {
            list.add( roomToRoomViewDto( room ) );
        }
        return list;
    }
}
