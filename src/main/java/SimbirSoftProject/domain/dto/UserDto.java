package SimbirSoftProject.domain.dto;

import java.util.Date;
import java.util.Set;

import SimbirSoftProject.domain.util.Role;
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
}
