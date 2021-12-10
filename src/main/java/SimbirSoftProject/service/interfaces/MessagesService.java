package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.domain.dto.MessageDto;
import SimbirSoftProject.domain.util.Messages;
import org.springframework.http.ResponseEntity;

public interface MessagesService {

    ResponseEntity<MessageDto> createMessage(MessageDto messageDto);
    Messages getMessageById(Long id);
    String deleteMessageById(Long id);
    String updateMessage(Long id);
}
