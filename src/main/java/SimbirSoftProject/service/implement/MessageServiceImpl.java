package SimbirSoftProject.service.implement;

import SimbirSoftProject.dto.MessageDto;
import SimbirSoftProject.model.Messages;
import SimbirSoftProject.mapper.MessageMapper;
import SimbirSoftProject.repository.MessageRepository;
import SimbirSoftProject.service.interfaces.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessagesService {

    private final MessageRepository messageRepository;
    @Override
    public void createMessage(MessageDto messageDto) {
        messageDto.setContent(messageDto.getContent());
        messageRepository.save(messageDto.messagesDtoTomMessage(messageDto));
    }

    @Override
    public MessageDto getMessageById(Long id) {
        return messageToMessageDTO(messageRepository.getById(id));
    }

    @Override
    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public void updateMessage(Long id) {
        Messages messages = messageRepository.getById(id);
        messageRepository.deleteById(id);
        messages.setContent(messages.getContent()); // ?
        messageRepository.save(messages);
    }
    private MessageDto messageToMessageDTO(Messages messages){
        return MessageMapper.MESSAGE_MAPPER.messageToMessageDto(messages);
    }
}
