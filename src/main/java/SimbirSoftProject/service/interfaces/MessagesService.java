package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.controller.dto.MessageDto;
import SimbirSoftProject.entity.Messages;

public interface MessagesService {

    void createMessage(MessageDto messageDto);
    MessageDto getMessageById(Long id);
    void deleteMessageById(Long id);
    void updateMessage(Long id);
}
