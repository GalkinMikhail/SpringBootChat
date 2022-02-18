package chat.service.implement;

import chat.dto.RoomDto;
import chat.dto.RoomRenameDto;
import chat.dto.RoomViewDto;
import chat.dto.UserToAddDto;
import chat.exceptions.ResourceNotFoundException;
import chat.exceptions.UserBlockedException;
import chat.mapper.RoomMapper;
import chat.model.Role;
import chat.model.Room;
import chat.model.User;
import chat.repository.RoomRepository;
import chat.repository.UserRepository;
import chat.security.SecurityUser;
import chat.service.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        User currentUser = userRepository.findByLogin(login);
        Room room = roomRepository.getById(id);
        boolean isAdmin = false;
        Set<Role> userRoles = currentUser.getRoles();
        for (Role role : userRoles){
            if (role.getName().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (!currentUser.getLogin().equals(room.getCreatorId().getLogin()) && !isAdmin){
            throw new AccessDeniedException("You have no permission to delete this room");
        }
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
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        User currentUser = userRepository.findByLogin(login);
        if (user == null){
            throw new ResourceNotFoundException("User with login" + userToAddDto.getLogin() + "not found", "user login");
        }
        boolean UserBlocked = currentUser.isBlocked();
        if (UserBlocked && !currentUser.getBlockDate().plusMinutes(currentUser.getBlockingDurationInMinutes()).isBefore(LocalDateTime.now())){
            throw new UserBlockedException("User " + user.getLogin() + " is blocked","user blocked");
        }
        Room room = roomRepository.getById(id);
        boolean isAdmin = false;
        Set<Role> userRoles = currentUser.getRoles();
        for (Role role : userRoles){
            if (role.getName().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (!currentUser.getLogin().equals(room.getCreatorId().getLogin()) && !isAdmin){
            throw new AccessDeniedException("You have no permission to delete participants from this room");
        }
        Set<User> listParticipants = room.getParticipants();
        if (!listParticipants.contains(user)){
            throw new ResourceNotFoundException("User is not a member of this room", "room participants");
        }
        listParticipants.remove(user);
        room.setParticipants(listParticipants);
        roomRepository.save(room);
    }

    @Override
    public void renameRoom(RoomRenameDto roomRenameDto, Long id) {
        Room room = roomRepository.getById(id);
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        User currentUser = userRepository.findByLogin(login);
        boolean UserBlocked = currentUser.isBlocked();
        if (UserBlocked && !currentUser.getBlockDate().plusMinutes(currentUser.getBlockingDurationInMinutes()).isBefore(LocalDateTime.now())){
            throw new UserBlockedException("User " + currentUser.getLogin() + " is blocked","user blocked");
        }
        boolean isAdmin = false;
        Set<Role> userRoles = currentUser.getRoles();
        for (Role role : userRoles){
            if (role.getName().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (!currentUser.getLogin().equals(room.getCreatorId().getLogin()) && !isAdmin) {
            throw new AccessDeniedException("You have no permission to rename this room");
        }
        room.setName(roomRenameDto.getName());
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
