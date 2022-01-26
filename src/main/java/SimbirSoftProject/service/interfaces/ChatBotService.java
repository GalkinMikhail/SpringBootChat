package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.dto.MessageResponseDTO;
import SimbirSoftProject.model.User;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ChatBotService {
    ResponseEntity<String> parser(MessageResponseDTO message, User user) throws IOException;
    ResponseEntity<String> user(MessageResponseDTO message, User user);
    ResponseEntity<String> room(MessageResponseDTO message, User user);
    ResponseEntity<String> youTube(MessageResponseDTO message, User user) throws IOException;
}
