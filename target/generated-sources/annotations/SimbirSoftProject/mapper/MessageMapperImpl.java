package SimbirSoftProject.mapper;

import SimbirSoftProject.controller.dto.MessageDto;
import SimbirSoftProject.entity.Messages;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-20T01:59:41+0300",
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
        messageDto.setCreated_at( entity.getCreated_at() );
        messageDto.setContent( entity.getContent() );

        return messageDto;
    }

    @Override
    public Messages messagesDtoToMessages(MessageDto dto) {
        if ( dto == null ) {
            return null;
        }

        Messages messages = new Messages();

        messages.setId( dto.getId() );
        messages.setCreated_at( dto.getCreated_at() );
        messages.setContent( dto.getContent() );

        return messages;
    }
}
