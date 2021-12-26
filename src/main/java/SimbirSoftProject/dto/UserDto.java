package SimbirSoftProject.dto;

import java.util.Date;
import java.util.List;

import SimbirSoftProject.model.Role;
import SimbirSoftProject.model.User;
import SimbirSoftProject.mapper.UserMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id; // ?
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private List<Role> roles;
    private boolean isUserOnline; // ?
    private boolean isBlocked; // ?
    private Date blockDate; // ?
    private Date blockingDuration; // ?

    public User userDtoToUser(UserDto newUser){
        return UserMapper.USER_MAPPER.userDtoToUser(newUser);
    }
    public UserDto userToUserDto(User user) {
        return UserMapper.USER_MAPPER.userToUserDto(user);
    }
}
