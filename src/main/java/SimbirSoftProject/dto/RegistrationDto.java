package SimbirSoftProject.dto;

import SimbirSoftProject.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
}
