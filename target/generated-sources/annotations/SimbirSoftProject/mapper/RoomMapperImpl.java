package SimbirSoftProject.mapper;

import SimbirSoftProject.dto.RoomDto;
import SimbirSoftProject.model.Room;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-25T22:56:06+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.1 (Oracle Corporation)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomDto roomToRoomDto(Room entity) {
        if ( entity == null ) {
            return null;
        }

        RoomDto roomDto = new RoomDto();

        roomDto.setId( entity.getId() );
        roomDto.setName( entity.getName() );
        roomDto.setType( entity.getType() );
        roomDto.setCreatorId( entity.getCreatorId() );

        return roomDto;
    }

    @Override
    public Room roomDtoToRoom(RoomDto dto) {
        if ( dto == null ) {
            return null;
        }

        Room room = new Room();

        room.setId( dto.getId() );
        room.setName( dto.getName() );
        room.setType( dto.getType() );
        room.setCreatorId( dto.getCreatorId() );

        return room;
    }

    @Override
    public List<RoomDto> allToDTO(List<Room> rooms) {
        if ( rooms == null ) {
            return null;
        }

        List<RoomDto> list = new ArrayList<RoomDto>( rooms.size() );
        for ( Room room : rooms ) {
            list.add( roomToRoomDto( room ) );
        }

        return list;
    }
}
