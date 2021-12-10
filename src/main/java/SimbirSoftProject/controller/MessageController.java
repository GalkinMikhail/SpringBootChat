package SimbirSoftProject.controller;

import SimbirSoftProject.domain.dto.MessageDto;
import SimbirSoftProject.domain.util.Messages;
import SimbirSoftProject.service.interfaces.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessagesService messagesService;
    @Autowired
    public MessageController(MessagesService messagesService){
        this.messagesService = messagesService;
    }
    @PostMapping("/create")
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto){
        messagesService.createMessage(messageDto);
        return ResponseEntity.ok(messageDto);
    }
    @GetMapping("/get/{id}")
    public Messages getMessageById(@PathVariable Long id){
        return messagesService.getMessageById(id);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteMessageById(@PathVariable Long id){
        messagesService.deleteMessageById(id);
        return "Message deleted successfully";
    }
    @PostMapping("/update/{id}")
    public String updateMessage(@PathVariable Long id){
        messagesService.updateMessage(id);
        return "Message was updated successfully";
    }
}
