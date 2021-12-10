package SimbirSoftProject.service.implement;

import SimbirSoftProject.domain.dto.MessageDto;
import SimbirSoftProject.domain.util.Messages;
import SimbirSoftProject.service.interfaces.MessagesService;
import org.springframework.http.ResponseEntity;

public class MessageServiceImpl implements MessagesService {
    @Override
    public ResponseEntity<MessageDto> createMessage(MessageDto messageDto) {
        return null;
    }

    @Override
    public Messages getMessageById(Long id) {
        return null;
    }

    @Override
    public String deleteMessageById(Long id) {
        return null;
    }

    @Override
    public String updateMessage(Long id) {
        return null;
    }
}
