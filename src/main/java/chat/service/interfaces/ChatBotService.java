package chat.service.interfaces;

import chat.dto.MessageResponseDTO;
import chat.model.User;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ChatBotService {
    ResponseEntity<String> parser(MessageResponseDTO message, User user) throws IOException;
    ResponseEntity<String> user(MessageResponseDTO message, User user);
    ResponseEntity<String> room(MessageResponseDTO message, User user);
    ResponseEntity<String> youTube(MessageResponseDTO message, User user) throws IOException;
}
