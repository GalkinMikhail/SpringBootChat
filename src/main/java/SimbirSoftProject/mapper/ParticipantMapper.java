package SimbirSoftProject.mapper;

import SimbirSoftProject.controller.dto.ParticipantsDto;
import SimbirSoftProject.entity.Participants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ParticipantMapper {

    ParticipantMapper PARTICIPANT_MAPPER = Mappers.getMapper(ParticipantMapper.class);
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "room_id", source = "room_id"),
            @Mapping(target = "user_id", source = "user_id"),
    })
    ParticipantsDto participantToParticipantDto(Participants entity);
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "room_id", source = "room_id"),
            @Mapping(target = "user_id", source = "user_id"),
    })
    Participants participantsDtoToParticipants(ParticipantsDto dto);
    List<ParticipantsDto> allToDTO(List<Participants> participantsList);
}
