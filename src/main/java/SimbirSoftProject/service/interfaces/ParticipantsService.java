package SimbirSoftProject.service.interfaces;

import SimbirSoftProject.domain.util.Participants;

import java.util.List;

public interface ParticipantsService {
    String addParticipant(Participants participants);
    List<Participants> getAll();
    String deleteParticipant(Participants participants);
    Participants getParticipantById(Long id);
}
