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
    ParticipantsDto participantToParticipantDto(Participants entity);
    Participants participantsDtoToParticipants(ParticipantsDto dto);
    List<ParticipantsDto> allToDTO(List<Participants> participantsList);
}
