package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.controller.dto.UserDto;
import SimbirSoftProject.entity.User;

import java.util.List;

public interface UserService {

    void createUser(UserDto userDto);

    UserDto getUserById(Long id);

    void deleteUserById(Long id);

    List<UserDto> getAll();
}
