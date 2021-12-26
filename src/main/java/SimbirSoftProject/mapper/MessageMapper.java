package SimbirSoftProject.mapper;

import SimbirSoftProject.dto.MessageDto;
import SimbirSoftProject.model.Messages;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageMapper MESSAGE_MAPPER = Mappers.getMapper(MessageMapper.class);

    MessageDto messageToMessageDto(Messages entity);

    Messages messagesDtoToMessages(MessageDto dto);
}
