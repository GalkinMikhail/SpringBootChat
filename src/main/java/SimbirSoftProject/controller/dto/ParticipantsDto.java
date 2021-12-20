package SimbirSoftProject.controller.dto;


import SimbirSoftProject.entity.Participants;
import SimbirSoftProject.entity.Room;
import SimbirSoftProject.entity.User;
import SimbirSoftProject.mapper.ParticipantMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsDto {
    private Long id;
    private Room roomId;
    private User userId;

    public Participants participantDtoToParticipant(ParticipantsDto participantsDto){
        return ParticipantMapper.PARTICIPANT_MAPPER.participantsDtoToParticipants(participantsDto);
    }
}
