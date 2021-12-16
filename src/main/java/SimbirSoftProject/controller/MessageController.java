package SimbirSoftProject.controller;

import SimbirSoftProject.controller.dto.MessageDto;
import SimbirSoftProject.entity.Messages;
import SimbirSoftProject.service.interfaces.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController{

    private final MessagesService messagesService;
    @PostMapping("/create")
    public ResponseEntity<String> createMessage(@RequestBody MessageDto messageDto){
        messagesService.createMessage(messageDto);
        return ResponseEntity.ok("");
    }
    @GetMapping("/get/{id}")
    public Messages getMessageById(@PathVariable Long id){
        return messagesService.getMessageById(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Long id){
        messagesService.deleteMessageById(id);
        return ResponseEntity.ok("Message was deleted successfully");
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateMessage(@PathVariable Long id){
        messagesService.updateMessage(id);
        return ResponseEntity.ok("Message was updated successfully");
    }
}
