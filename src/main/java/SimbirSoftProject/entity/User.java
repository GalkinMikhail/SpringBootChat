package SimbirSoftProject.entity;

import SimbirSoftProject.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_login", columnNames = "login")
})
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Login cannot be empty")
    @Column(name = "login")
    private String login;

    @NotBlank(message = "Password cannot be empty")
    @Column(name = "password")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @NotBlank(message = "Firstname cannot be empty")
    @Column(name = "firstname")
    private String firstName;

    @Size(max = 30)
    @NotBlank(message = "Lastname cannot be empty")
    @Column(name = "lastname")
    private String lastName;

    @Column(name = "is_user_online")
    private boolean isUserOnline;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Temporal(TemporalType.DATE)
    @Column(name = "block_date")
    private Date blockDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "blocking_duration")
    private Date blockingDuration;

}
