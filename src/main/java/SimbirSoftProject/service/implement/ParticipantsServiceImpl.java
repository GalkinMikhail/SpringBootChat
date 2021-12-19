package SimbirSoftProject.service.implement;

import SimbirSoftProject.controller.dto.ParticipantsDto;
import SimbirSoftProject.entity.Participants;
import SimbirSoftProject.mapper.ParticipantMapper;
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
    public void addParticipant(ParticipantsDto participantsDto) {
        participantsRepository.save(participantsDto.participantDtoToParticipant(participantsDto));
    }

    @Override
    public List<ParticipantsDto> getAll() {
        return allToDTO(participantsRepository.findAll());
    }

    @Override
    public void deleteParticipantById(Long id) {
        participantsRepository.deleteById(id);
    }

    @Override
    public ParticipantsDto getParticipantById(Long id) {
        return participantToParticipantDTO(participantsRepository.getById(id));
    }

    private List<ParticipantsDto> allToDTO(List<Participants> participantsList){
        return ParticipantMapper.PARTICIPANT_MAPPER.allToDTO(participantsList);
    }
    private ParticipantsDto participantToParticipantDTO(Participants participants){
        return ParticipantMapper.PARTICIPANT_MAPPER.participantToParticipantDto(participants);
    }
}
