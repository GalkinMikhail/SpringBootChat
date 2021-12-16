package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.controller.dto.MessageDto;
import SimbirSoftProject.entity.Messages;

public interface MessagesService {

    String createMessage(MessageDto messageDto);
    Messages getMessageById(Long id);
    String deleteMessageById(Long id);
    String updateMessage(Long id);
}
