package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.controller.dto.UserDto;
import SimbirSoftProject.entity.User;

import java.util.List;

public interface UserService {

    String createUser(UserDto userDto);

    User getUserById(Long id);

    String deleteUserById(Long id);

    List<User> getAll();
}
