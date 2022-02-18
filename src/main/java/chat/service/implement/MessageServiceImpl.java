package chat.service.implement;

import chat.dto.MessageDto;
import chat.exceptions.ResourceNotFoundException;
import chat.exceptions.UserBlockedException;
import chat.model.Messages;
import chat.mapper.MessageMapper;
import chat.model.Room;
import chat.model.User;
import chat.repository.MessageRepository;
import chat.repository.RoomRepository;
import chat.repository.UserRepository;
import chat.service.interfaces.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if (UserBlocked && !user.getBlockDate().plusMinutes(user.getBlockingDurationInMinutes()).isBefore(LocalDateTime.now())){
            throw new UserBlockedException("User " + user.getLogin() + " is blocked","user blocked"); // сделать вывод остатка блокировки
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
        if (UserBlocked && !user.getBlockDate().plusMinutes(user.getBlockingDurationInMinutes()).isBefore(LocalDateTime.now())){
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
