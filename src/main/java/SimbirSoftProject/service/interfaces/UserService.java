package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.dto.RegistrationDto;
import SimbirSoftProject.dto.UserBlockDto;
import SimbirSoftProject.dto.UserDto;
import SimbirSoftProject.dto.UserToAddDto;
import SimbirSoftProject.model.User;

import java.util.List;

public interface UserService {

    void registration(RegistrationDto registrationDto);

    UserDto getUserById(Long id);

    void deleteUserById(Long id);

    List<UserDto> getAll();

    User findByLogin(String login);

    void blockUser(Long id, UserBlockDto userBlockDto);

    void unBlockUser(Long id);

    void setModerator(Long id);

    void deleteModerator(Long id);

    void renameUser(UserToAddDto userToAddDto,Long id);
}
