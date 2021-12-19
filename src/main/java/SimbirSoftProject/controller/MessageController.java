package SimbirSoftProject.controller;

import SimbirSoftProject.controller.dto.MessageDto;
import SimbirSoftProject.entity.Messages;
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.messagesService.createMessage(messageDto);
        return new ResponseEntity<>(messageDto,HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MessageDto messageDto = this.messagesService.getMessageById(id);
        if (messageDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(messageDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDto> deleteMessageById(@PathVariable Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MessageDto messageDto = this.messagesService.getMessageById(id);
        if(messageDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.messagesService.deleteMessageById(id);
        return new ResponseEntity<>(messageDto,HttpStatus.OK);
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<MessageDto> updateMessage(@PathVariable Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MessageDto messageDto = this.messagesService.getMessageById(id);
        if(messageDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        messagesService.updateMessage(id);
        return new ResponseEntity<>(messageDto,HttpStatus.OK);
    }
}
