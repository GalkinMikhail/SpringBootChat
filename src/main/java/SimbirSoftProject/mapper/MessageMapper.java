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
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "created_at", source = "created_at"),
            @Mapping(target = "updated_at", source = "updatedAt"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "to_id", source = "toId"),
            @Mapping(target = "from_id", source = "fromId"),
            @Mapping(target = "room_id", source = "roomId")
    })
    MessageDto messageToMessageDto(Messages entity);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "created_at", source = "created_at"),
            @Mapping(target = "updatedAt", source = "updated_at"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "toId", source = "to_id"),
            @Mapping(target = "fromId", source = "from_id"),
            @Mapping(target = "roomId", source = "room_id")
    })
    Messages messagesDtoToMessages(MessageDto dto);
}
