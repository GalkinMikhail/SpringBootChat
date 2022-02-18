package chat.controller;

import chat.dto.AuthenticationRequestDTO;
import chat.model.User;
import chat.repository.UserRepository;
import chat.security.JwtTokenProvider;
import chat.security.SecurityUser;
import chat.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationRestController {


    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final UserRepository userRepository;


    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO requestDTO){
        try {
            String login = requestDTO.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login,requestDTO.getPassword()));
            User user = userService.findByLogin(login);

            if(user == null){
                throw new UsernameNotFoundException("User with login" + login + "not found");
            }

            String token = jwtTokenProvider.createToken(login,user.getRoles());
            Map<Object,Object> response = new HashMap<>();
            response.put("login", requestDTO.getLogin());
            response.put("token", token);
            user.setUserOnline(true);
            userRepository.save(user);
            return ResponseEntity.ok(response);

        }
        catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid login or password",HttpStatus.FORBIDDEN);
        }

    }
    @PostMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        User user = userService.findByLogin(login);
        user.setUserOnline(false);
        userRepository.save(user);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request,response,null);
    }
}
