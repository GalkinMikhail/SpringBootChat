package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.controller.dto.ParticipantsDto;
import SimbirSoftProject.entity.Participants;

import java.util.List;

public interface ParticipantsService {
    String addParticipant(ParticipantsDto participantsDto);
    List<Participants> getAll();
    String deleteParticipantById(Long id);
//    Participants getParticipantById(Long id);
}
