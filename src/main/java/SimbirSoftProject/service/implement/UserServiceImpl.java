package SimbirSoftProject.service.implement;

import SimbirSoftProject.domain.dto.UserDto;
import SimbirSoftProject.domain.util.User;
import SimbirSoftProject.repository.UserRepository;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(UserDto userDto) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public String deleteUserById(Long id) {
        return "User deleted successful";
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
