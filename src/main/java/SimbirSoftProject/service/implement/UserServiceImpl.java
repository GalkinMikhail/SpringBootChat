package SimbirSoftProject.service.implement;

import SimbirSoftProject.dto.RegistrationDto;
import SimbirSoftProject.dto.UserBlockDto;
import SimbirSoftProject.dto.UserDto;
import SimbirSoftProject.dto.UserToAddDto;
import SimbirSoftProject.mapper.UserMapper;
import SimbirSoftProject.model.Role;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.RoomType;
import SimbirSoftProject.model.User;
import SimbirSoftProject.repository.RoleRepository;
import SimbirSoftProject.repository.RoomRepository;
import SimbirSoftProject.repository.UserRepository;
import SimbirSoftProject.security.SecurityUser;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoomRepository roomRepository;

    @Override
    public void registration(RegistrationDto registrationDto) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        User user = new User();
        user.setLogin(registrationDto.getLogin());
        String encodedPass = new BCryptPasswordEncoder().encode(registrationDto.getPassword());
        user.setPassword(encodedPass);
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setRoles(userRoles);
        userRepository.save(user);
        Room botRoom = new Room();
        botRoom.setName("ChatBotRoomFor"+user.getLogin());
        botRoom.setType(RoomType.PRIVATE);
        botRoom.setCreatorId(user);
        Set<User> addUserToChatBotRoom = new HashSet<>();
        addUserToChatBotRoom.add(user);
        botRoom.setParticipants(addUserToChatBotRoom);
        roomRepository.save(botRoom);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userToUserDto(userRepository.getById(id));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAll() {
        return allToDTO(userRepository.findAll());
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void blockUser(Long id, UserBlockDto userBlockDto) {
        User user = userRepository.getById(id);
        user.setBlocked(true);
        user.setBlockDate(LocalDateTime.now());
        user.setBlockingDurationInMinutes(userBlockDto.getBlockTime());
        userRepository.save(user);
    }

    @Override
    public void unBlockUser(Long id) {
        User user = userRepository.getById(id);
        user.setBlocked(false);
        userRepository.save(user);
    }

    @Override
    public void setModerator(Long id) {
        boolean isUserAdmin = false;
        User user = userRepository.getById(id);
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        User currentUser = userRepository.findByLogin(login);
        Set<Role> currentUserRoles = currentUser.getRoles();
        for (Role role : currentUserRoles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                isUserAdmin = true;
            }
        }
        if (isUserAdmin) {
            Set<Role> newRole = new HashSet<>();
            Role roleUser = roleRepository.findByName("ROLE_MODERATOR");
            newRole.add(roleUser);
            user.setRoles(newRole);
            userRepository.save(user);
        }
        else
            throw new AccessDeniedException("You have no permission to set moderators");
    }

    @Override
    public void deleteModerator(Long id) {
        boolean isUserAdmin = false;
        User user = userRepository.getById(id);
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        User currentUser = userRepository.findByLogin(login);
        Set<Role> currentUserRoles = currentUser.getRoles();
        for (Role role : currentUserRoles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                isUserAdmin = true;
            }
        }
        if (isUserAdmin) {
            Set<Role> newRole = new HashSet<>();
            Role roleUser = roleRepository.findByName("ROLE_USER");
            newRole.add(roleUser);
            user.setRoles(newRole);
            userRepository.save(user);
        }
        else
            throw new AccessDeniedException("You have no permission to delete moderators");
    }

    @Override
    public void renameUser(UserToAddDto userToAddDto, Long id){
        User user = userRepository.getById(id);
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        User currentUser = userRepository.findByLogin(login);
        boolean isAdmin = false;
        Set<Role> userRoles = currentUser.getRoles();
        for (Role role : userRoles){
            if (role.getName().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (currentUser.getLogin().equals(user.getLogin()) || isAdmin){
            user.setLogin(userToAddDto.getLogin());
        }
        else{
            throw new AccessDeniedException("You have no permission to rename this user");
        }
        userRepository.save(user);
    }

    private UserDto userToUserDto(User user){
        return UserMapper.USER_MAPPER.userToUserDto(user);
    }
    private List<UserDto> allToDTO(List<User> userList){
        return UserMapper.USER_MAPPER.allToDto(userList);
    }
    private User userDtoToUser(UserDto userDto){
        return UserMapper.USER_MAPPER.userDtoToUser(userDto);
    }

}
