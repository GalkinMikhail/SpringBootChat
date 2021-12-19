package SimbirSoftProject.mapper;

import SimbirSoftProject.controller.dto.ParticipantsDto;
import SimbirSoftProject.entity.Participants;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-20T01:59:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.1 (Oracle Corporation)"
)
@Component
public class ParticipantMapperImpl implements ParticipantMapper {

    @Override
    public ParticipantsDto participantToParticipantDto(Participants entity) {
        if ( entity == null ) {
            return null;
        }

        ParticipantsDto participantsDto = new ParticipantsDto();

        participantsDto.setId( entity.getId() );
        participantsDto.setRoom_id( entity.getRoom_id() );
        participantsDto.setUser_id( entity.getUser_id() );

        return participantsDto;
    }

    @Override
    public Participants participantsDtoToParticipants(ParticipantsDto dto) {
        if ( dto == null ) {
            return null;
        }

        Participants participants = new Participants();

        participants.setId( dto.getId() );
        participants.setRoom_id( dto.getRoom_id() );
        participants.setUser_id( dto.getUser_id() );

        return participants;
    }

    @Override
    public List<ParticipantsDto> allToDTO(List<Participants> participantsList) {
        if ( participantsList == null ) {
            return null;
        }

        List<ParticipantsDto> list = new ArrayList<ParticipantsDto>( participantsList.size() );
        for ( Participants participants : participantsList ) {
            list.add( participantToParticipantDto( participants ) );
        }

        return list;
    }
}
