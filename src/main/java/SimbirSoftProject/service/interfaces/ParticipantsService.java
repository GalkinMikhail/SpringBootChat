package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.dto.ParticipantsDto;

import java.util.List;

public interface ParticipantsService {
    void addParticipant(ParticipantsDto participantsDto);
    List<ParticipantsDto> getAll();
    void deleteParticipantById(Long id);
    ParticipantsDto getParticipantById(Long id);
}
