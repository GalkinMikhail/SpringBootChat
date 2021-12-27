package SimbirSoftProject.service.implement;

import SimbirSoftProject.dto.UserDto;
import SimbirSoftProject.model.Role;
import SimbirSoftProject.model.User;
import SimbirSoftProject.exceptions.PersistenceLayerException;
import SimbirSoftProject.mapper.UserMapper;
import SimbirSoftProject.repository.RoleRepository;
import SimbirSoftProject.repository.UserRepository;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Override
    public void registration(UserDto userDto) {
        String login = userDto.getLogin();
        User user = findByLogin(login);
        if (user != null){
            throw new PersistenceLayerException("User with login " + login + " already exists", "user login");
        }
        userDto.setLogin(userDto.getLogin());

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        //userDto.setRoles(userRoles);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setFirstName(userDto.getFirstName());
        userDto.setLastName(userDto.getLastName());


        userRepository.save(userDtoToUser(userDto));
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
