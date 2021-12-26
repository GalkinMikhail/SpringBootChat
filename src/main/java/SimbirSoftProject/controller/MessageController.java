package SimbirSoftProject.controller;

import SimbirSoftProject.dto.MessageDto;
import SimbirSoftProject.exceptions.ResourceNotFoundException;
import SimbirSoftProject.service.interfaces.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController{

    private final MessagesService messagesService;
    @PostMapping("/create")
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto){
        if (messageDto == null){
            throw new ResourceNotFoundException("Failed to create the message", "message content");
        }
        this.messagesService.createMessage(messageDto);
        return new ResponseEntity<>(messageDto,HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable Long id){
        MessageDto messageDto = this.messagesService.getMessageById(id);
        if (messageDto == null){
            throw new ResourceNotFoundException("Message with id " + id + "not found", "message id");
        }
        return ResponseEntity.ok(messageDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDto> deleteMessageById(@PathVariable Long id){
        MessageDto messageDto = this.messagesService.getMessageById(id);
        if(messageDto == null){
            throw new ResourceNotFoundException("Message with id " + id + "not found", "message id");
        }
        this.messagesService.deleteMessageById(id);
        return new ResponseEntity<>(messageDto,HttpStatus.OK);
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<MessageDto> updateMessage(@PathVariable Long id){
        MessageDto messageDto = this.messagesService.getMessageById(id);
        if(messageDto == null){
            throw new ResourceNotFoundException("Message with id " + id + "not found", "message id");
        }
        messagesService.updateMessage(id);
        return new ResponseEntity<>(messageDto,HttpStatus.OK);
    }
}
