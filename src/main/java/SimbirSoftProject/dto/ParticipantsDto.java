package SimbirSoftProject.dto;


import SimbirSoftProject.model.Participants;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.User;
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
