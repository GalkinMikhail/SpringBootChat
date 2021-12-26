package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.dto.MessageDto;

public interface MessagesService {

    void createMessage(MessageDto messageDto);
    MessageDto getMessageById(Long id);
    void deleteMessageById(Long id);
    void updateMessage(Long id);
}
