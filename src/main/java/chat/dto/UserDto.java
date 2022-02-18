package chat.dto;

import chat.mapper.UserMapper;
import chat.model.Role;
import chat.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private boolean isUserOnline;
    private boolean isBlocked;
    private LocalDateTime blockDate;
    private Long blockingDurationInMinutes;

    public User userDtoToUser(UserDto newUser){
        return UserMapper.USER_MAPPER.userDtoToUser(newUser);
    }
    public UserDto userToUserDto(User user) {
        return UserMapper.USER_MAPPER.userToUserDto(user);
    }
}
