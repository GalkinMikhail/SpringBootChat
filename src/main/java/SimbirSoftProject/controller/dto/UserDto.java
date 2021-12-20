package SimbirSoftProject.controller.dto;

import java.util.Date;
import java.util.Set;

import SimbirSoftProject.entity.Role;
import SimbirSoftProject.entity.User;
import SimbirSoftProject.mapper.UserMapper;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private Set<Role> roles;
    private String firstName;
    private String lastName;
    private boolean isUserOnline;
    private boolean isBlocked;
    private Date blockDate;
    private Date blockingDuration;

    public User userDtoToUser(UserDto newUser){
        return UserMapper.USER_MAPPER.userDtoToUser(newUser);
    }

}
