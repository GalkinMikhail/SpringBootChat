package chat.service.interfaces;

import chat.dto.MessageDto;
import chat.model.User;


public interface MessagesService {

    void sendMessage(MessageDto messageDto, User user);
    MessageDto getMessageById(Long id);
    void deleteMessageById(Long id);
    void updateMessage(Long id);
}
