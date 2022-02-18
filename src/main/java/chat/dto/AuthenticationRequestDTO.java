package chat.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthenticationRequestDTO {
    private String login;
    private String password;
}
