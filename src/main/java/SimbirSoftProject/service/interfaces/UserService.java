package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.dto.UserDto;
import SimbirSoftProject.model.User;

import java.util.List;

public interface UserService {

    void registration(UserDto userDto);

    UserDto getUserById(Long id);

    void deleteUserById(Long id);

    List<UserDto> getAll();

    User findByLogin(String login);
}
