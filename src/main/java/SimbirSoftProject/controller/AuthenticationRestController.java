package SimbirSoftProject.controller;

import SimbirSoftProject.dto.AuthenticationRequestDTO;
import SimbirSoftProject.model.User;
import SimbirSoftProject.security.JwtTokenProvider;
import SimbirSoftProject.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
@RequestMapping(value = "api/v1/auth")
public class AuthenticationRestController {


    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;


    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO requestDTO){
        try {
            String login = requestDTO.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login,requestDTO.getPassword()));
            User user = userService.findByLogin(login);

            if(user == null){
                throw new UsernameNotFoundException("User with login" + login + "Not found");
            }

            String token = jwtTokenProvider.createToken(login,user.getRoles());
            Map<Object,Object> response = new HashMap<>();
            response.put("login", requestDTO.getLogin());
            response.put("token", token);

            return ResponseEntity.ok(response);

        }
        catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid login or password",HttpStatus.FORBIDDEN);
        }

    }
    @PostMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request,response,null);
    }
}
