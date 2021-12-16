package SimbirSoftProject.service.implement;

import SimbirSoftProject.controller.dto.UserDto;
import SimbirSoftProject.entity.User;
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
    public String createUser(UserDto userDto) {
        userRepository.save(userDto.userDtoToUser(userDto));
        return "User created successfully";
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public String deleteUserById(Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
