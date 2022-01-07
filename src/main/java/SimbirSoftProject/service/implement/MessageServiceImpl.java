package SimbirSoftProject.service.implement;

import SimbirSoftProject.dto.MessageDto;
import SimbirSoftProject.exceptions.ResourceNotFoundException;
import SimbirSoftProject.exceptions.UserBlockedException;
import SimbirSoftProject.model.Messages;
import SimbirSoftProject.mapper.MessageMapper;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.User;
import SimbirSoftProject.repository.MessageRepository;
import SimbirSoftProject.repository.RoomRepository;
import SimbirSoftProject.repository.UserRepository;
import SimbirSoftProject.service.interfaces.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessagesService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public void sendMessage(MessageDto messageDto, User user) {
        Optional<Room> roomToSendMessage = roomRepository.findByName(messageDto.getRoomName());
        if (!roomToSendMessage.isPresent()) {
            throw new ResourceNotFoundException("Room not found","room id");
        }
        boolean UserBlocked = user.isBlocked();
        if (UserBlocked){
            throw new UserBlockedException("User " + user.getLogin() + " is blocked","user blocked");
        }
        Messages messagesToSend = Messages.builder()
                .content(messageDto.getContent())
                .createdAt(messageDto.getCreatedAt())
                .author(user)
                .roomId(roomToSendMessage.get())
                .build();
        messageRepository.save(messagesToSend);
    }

    @Override
    public MessageDto getMessageById(Long id) {
        return messageToMessageDTO(messageRepository.getById(id));
    }

    @Override
    public void deleteMessageById(Long id) {
        Messages messages = messageRepository.getById(id);
        User user = messages.getAuthor();
        boolean UserBlocked = user.isBlocked();
        if (UserBlocked){
            throw new UserBlockedException("User " + user.getLogin() + " is blocked","user blocked");
        }
        messageRepository.deleteById(id);
    }

    @Override
    public void updateMessage(Long id) {
        Messages messages = messageRepository.getById(id);
        messageRepository.deleteById(id);
        messages.setContent(messages.getContent());
        messageRepository.save(messages);
    }
    private MessageDto messageToMessageDTO(Messages messages){
        return MessageMapper.MESSAGE_MAPPER.messageToMessageDto(messages);
    }
}
