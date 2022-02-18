package chat.controller;

import chat.dto.MessageResponseDTO;
import chat.model.User;
import chat.service.interfaces.ChatBotService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("bot")
public class ChatBotController {
    private final ChatBotService chatBotService;

    @PostMapping
    public ResponseEntity<String> send(@RequestBody MessageResponseDTO messageResponseDTO, User user){
        try {
            return chatBotService.parser(messageResponseDTO,user);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
