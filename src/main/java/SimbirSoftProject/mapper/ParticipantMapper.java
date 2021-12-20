package SimbirSoftProject.mapper;

import SimbirSoftProject.controller.dto.ParticipantsDto;
import SimbirSoftProject.entity.Participants;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ParticipantMapper {

    ParticipantMapper PARTICIPANT_MAPPER = Mappers.getMapper(ParticipantMapper.class);

    @Named("participantToParticipantDto")
    ParticipantsDto participantToParticipantDto(Participants entity);
    Participants participantsDtoToParticipants(ParticipantsDto dto);
    @IterableMapping(qualifiedByName = "participantToParticipantDto")
    List<ParticipantsDto> allToDTO(List<Participants> participantsList);
}
