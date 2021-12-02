package SimbirSoftProject.domain.util;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Login cannot be empty")
    private String login;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    @NotBlank(message = "Firstname cannot be empty")
    private String firstName;

    @Size(max = 30)
    @NotBlank(message = "Lastname cannot be empty")
    private String lastName;

    private boolean isUserOnline;

    private boolean isBlocked;

    private Date blockDate;

    private Date blockingDuration;

    public User(){

    }
}
