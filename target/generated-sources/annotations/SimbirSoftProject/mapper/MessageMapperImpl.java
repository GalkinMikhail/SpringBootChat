package SimbirSoftProject.mapper;

import SimbirSoftProject.dto.MessageDto;
import SimbirSoftProject.dto.MessageDto.MessageDtoBuilder;
import SimbirSoftProject.model.Messages;
import SimbirSoftProject.model.Messages.MessagesBuilder;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-08T02:07:06+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.1 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDto messageToMessageDto(Messages entity) {
        if ( entity == null ) {
            return null;
        }

        MessageDtoBuilder messageDto = MessageDto.builder();

        messageDto.createdAt( entity.getCreatedAt() );
        messageDto.content( entity.getContent() );

        return messageDto.build();
    }

    @Override
    public Messages messagesDtoToMessages(MessageDto dto) {
        if ( dto == null ) {
            return null;
        }

        MessagesBuilder messages = Messages.builder();

        messages.createdAt( dto.getCreatedAt() );
        messages.content( dto.getContent() );

        return messages.build();
    }
}
