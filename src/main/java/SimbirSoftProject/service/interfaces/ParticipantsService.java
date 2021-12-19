package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.controller.dto.ParticipantsDto;
import SimbirSoftProject.entity.Participants;

import java.util.List;

public interface ParticipantsService {
    void addParticipant(ParticipantsDto participantsDto);
    List<ParticipantsDto> getAll();
    void deleteParticipantById(Long id);
    ParticipantsDto getParticipantById(Long id);
}
