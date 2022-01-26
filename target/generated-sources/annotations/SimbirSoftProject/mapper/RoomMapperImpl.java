package SimbirSoftProject.mapper;

import SimbirSoftProject.dto.RoomDto;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.Room.RoomBuilder;
import SimbirSoftProject.model.RoomType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-26T21:00:19+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Azul Systems, Inc.)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomDto roomToRoomDto(Room entity) {
        if ( entity == null ) {
            return null;
        }

        String name = null;
        RoomType type = null;

        name = entity.getName();
        type = entity.getType();

        RoomDto roomDto = new RoomDto( name, type );

        return roomDto;
    }

    @Override
    public Room roomDtoToRoom(RoomDto dto) {
        if ( dto == null ) {
            return null;
        }

        RoomBuilder room = Room.builder();

        room.name( dto.getName() );
        room.type( dto.getType() );

        return room.build();
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
