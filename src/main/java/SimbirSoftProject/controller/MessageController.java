package SimbirSoftProject.controller;

import SimbirSoftProject.dto.MessageDto;
import SimbirSoftProject.exceptions.ResourceNotFoundException;
import SimbirSoftProject.model.User;
import SimbirSoftProject.security.SecurityUser;
import SimbirSoftProject.service.interfaces.MessagesService;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController{

    private final MessagesService messagesService;
    private final UserService userService;
    @PostMapping("/send")
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto){
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        User user = userService.findByLogin(login);
        if (messageDto == null){
            throw new ResourceNotFoundException("Failed to send the message", "message content, room id");
        }
            this.messagesService.sendMessage(messageDto, user);
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
    @PreAuthorize("hasAuthority('ROLE_MODERATOR') OR hasAuthority('ROLE_ADMIN')")
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
