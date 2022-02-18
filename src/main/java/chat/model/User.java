package chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {
            @UniqueConstraint(columnNames = "login")
})
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @NotBlank(message = "Firstname cannot be empty")
    private String firstName;

    @Size(max = 30)
    @NotBlank(message = "Lastname cannot be empty")
    private String lastName;

    @Column(name = "is_user_online")
    private boolean isUserOnline;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "last_block_date")
    private LocalDateTime blockDate;

    @Column(name = "last_blocking_duration_minutes")
    private Long blockingDurationInMinutes;

    public User(String login, String password, String firstName, String lastName, boolean isUserOnline, boolean isBlocked, LocalDateTime blockDate, Long blockingDuration) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isUserOnline = isUserOnline;
        this.isBlocked = isBlocked;
        this.blockDate = blockDate;
        this.blockingDurationInMinutes = blockingDuration;
    }
}
