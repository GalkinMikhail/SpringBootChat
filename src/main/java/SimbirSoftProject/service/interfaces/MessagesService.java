package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.dto.MessageDto;
import SimbirSoftProject.dto.RoomDto;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.User;


public interface MessagesService {

    void sendMessage(MessageDto messageDto, User user);
    MessageDto getMessageById(Long id);
    void deleteMessageById(Long id);
    void updateMessage(Long id);
}
