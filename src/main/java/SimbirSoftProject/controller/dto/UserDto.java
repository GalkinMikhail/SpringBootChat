package SimbirSoftProject.controller.dto;

import java.util.Date;
import java.util.Set;

import SimbirSoftProject.entity.Role;
import SimbirSoftProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        User user = new User();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setLogin(newUser.getLogin());
        user.setPassword(newUser.getPassword());
        return user;
    }
}
