package SimbirSoftProject.controller.dto;


import SimbirSoftProject.entity.Participants;
import SimbirSoftProject.entity.Room;
import SimbirSoftProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsDto {
    private Long id;
    private Room room_id;
    private User user_id;

    public Participants participantDtoToParticipant(ParticipantsDto participantsDto){
        Participants participant = new Participants();
        participant.setRoom_id(participant.getRoom_id());
        participant.setUser_id(participant.getUser_id());
        return participant;
    }
}
