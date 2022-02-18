package chat.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
public class SecurityUser implements UserDetails {
    @JsonIgnore
    @Getter
    private final Long Id;
    private final String login;
    private final String password;
    @Getter
    private final String firstName;
    @Getter
    private final String lastName;
    @Getter
    private final boolean isUserOnline;
    @Getter
    private final boolean isBlocked;
    @Getter
    private final LocalDateTime blockDate;
    @Getter
    private final Long blockingDurationInMinutes;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getLogin(){
        return login;
    }
}
