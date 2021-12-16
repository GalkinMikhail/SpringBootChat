package SimbirSoftProject.service.implement;

import SimbirSoftProject.controller.dto.MessageDto;
import SimbirSoftProject.entity.Messages;
import SimbirSoftProject.repository.MessageRepository;
import SimbirSoftProject.service.interfaces.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessagesService {

    private final MessageRepository messageRepository;
    @Override
    public String createMessage(MessageDto messageDto) {
        messageRepository.save(messageDto.messagesDtoTomMessage(messageDto));
        return "";
    }

    @Override
    public Messages getMessageById(Long id) {
        return messageRepository.getById(id);
    }

    @Override
    public String deleteMessageById(Long id) {
        messageRepository.deleteById(id);
        return "Message was deleted successfully";
    }

    @Override
    public String updateMessage(Long id) {
        Messages messages = messageRepository.getById(id);
        messageRepository.deleteById(id);
        messageRepository.save(messages);
        return "Message updated successfully";
    }
}
