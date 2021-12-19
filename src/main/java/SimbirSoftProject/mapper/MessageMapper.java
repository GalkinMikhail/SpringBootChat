package SimbirSoftProject.mapper;

import SimbirSoftProject.controller.dto.MessageDto;
import SimbirSoftProject.entity.Messages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageMapper MESSAGE_MAPPER = Mappers.getMapper(MessageMapper.class);

    MessageDto messageToMessageDto(Messages entity);

    Messages messagesDtoToMessages(MessageDto dto);
}
