package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.domain.dto.UserDto;
import SimbirSoftProject.domain.util.User;

import java.util.List;

public interface UserService {

    String createUser(UserDto userDto);

    User getUserById(Long id);

    String deleteUserById(Long id);

    List<User> getAll();
}
