package SimbirSoftProject.mapper;

import SimbirSoftProject.dto.MessageDto;
import SimbirSoftProject.model.Messages;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-25T22:56:06+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.1 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDto messageToMessageDto(Messages entity) {
        if ( entity == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setId( entity.getId() );
        messageDto.setCreatedAt( entity.getCreatedAt() );
        messageDto.setUpdatedAt( entity.getUpdatedAt() );
        messageDto.setContent( entity.getContent() );
        messageDto.setToId( entity.getToId() );
        messageDto.setFromId( entity.getFromId() );
        messageDto.setRoomId( entity.getRoomId() );

        return messageDto;
    }

    @Override
    public Messages messagesDtoToMessages(MessageDto dto) {
        if ( dto == null ) {
            return null;
        }

        Messages messages = new Messages();

        messages.setId( dto.getId() );
        messages.setCreatedAt( dto.getCreatedAt() );
        messages.setUpdatedAt( dto.getUpdatedAt() );
        messages.setContent( dto.getContent() );
        messages.setToId( dto.getToId() );
        messages.setFromId( dto.getFromId() );
        messages.setRoomId( dto.getRoomId() );

        return messages;
    }
}
