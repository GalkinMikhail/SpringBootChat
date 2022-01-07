package SimbirSoftProject.security;

import SimbirSoftProject.dto.UserDto;
import SimbirSoftProject.model.User;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findByLogin(login);

        if (user == null){
            throw new UsernameNotFoundException("User with login: " + login + "not found");
        }

        SecurityUser securityUser = JwtUserFactory.create(user);
        return securityUser;
    }

}
