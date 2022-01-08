package SimbirSoftProject.service.implement;

import SimbirSoftProject.dto.RegistrationDto;
import SimbirSoftProject.dto.UserBlockDto;
import SimbirSoftProject.dto.UserDto;
import SimbirSoftProject.mapper.UserMapper;
import SimbirSoftProject.model.Role;
import SimbirSoftProject.model.User;
import SimbirSoftProject.repository.RoleRepository;
import SimbirSoftProject.repository.UserRepository;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
        User user = userRepository.getById(id);
        Set<Role> newRole = new HashSet<>();
        Role roleUser = roleRepository.findByName("ROLE_MODERATOR");
        newRole.add(roleUser);
        user.setRoles(newRole);
        userRepository.save(user);
    }

    @Override
    public void deleteModerator(Long id) {
        User user = userRepository.getById(id);
        Set<Role> newRole = new HashSet<>();
        Role roleUser = roleRepository.findByName("ROLE_USER");
        newRole.add(roleUser);
        user.setRoles(newRole);
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
