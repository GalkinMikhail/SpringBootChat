package SimbirSoftProject.service.implement;

import SimbirSoftProject.controller.dto.ParticipantsDto;
import SimbirSoftProject.entity.Participants;
import SimbirSoftProject.repository.ParticipantsRepository;
import SimbirSoftProject.service.interfaces.ParticipantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ParticipantsServiceImpl implements ParticipantsService {
    private final ParticipantsRepository participantsRepository;
    @Override
    public String addParticipant(ParticipantsDto participantsDto) {
        participantsRepository.save(participantsDto.participantDtoToParticipant(participantsDto));
        return "Participant was added successfully";
    }

    @Override
    public List<Participants> getAll() {
        return participantsRepository.findAll();
    }

    @Override
    public String deleteParticipantById(Long id) {
        participantsRepository.deleteById(id);
        return "Participant was deleted successfully";
    }

    //@Override
    //public Participants getParticipantById(Long id) {
    //    return participantsRepository.getById(id);
    //}
}
