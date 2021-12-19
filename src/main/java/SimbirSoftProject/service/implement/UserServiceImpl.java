package SimbirSoftProject.service.implement;

import SimbirSoftProject.controller.dto.UserDto;
import SimbirSoftProject.entity.User;
import SimbirSoftProject.mapper.UserMapper;
import SimbirSoftProject.repository.UserRepository;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void createUser(UserDto userDto) {
        userRepository.save(userDto.userDtoToUser(userDto));
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
    private UserDto userToUserDto(User user){
        return UserMapper.USER_MAPPER.userToUserDto(user);
    }
    private List<UserDto> allToDTO(List<User> userList){
        return UserMapper.USER_MAPPER.allToDto(userList);
    }
}
