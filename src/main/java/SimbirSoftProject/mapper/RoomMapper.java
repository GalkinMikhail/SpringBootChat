package SimbirSoftProject.mapper;

import SimbirSoftProject.controller.dto.RoomDto;
import SimbirSoftProject.entity.Room;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomMapper ROOM_MAPPER = Mappers.getMapper(RoomMapper.class);

    @Named("roomToRoomDto")
    RoomDto roomToRoomDto(Room entity);
    Room roomDtoToRoom(RoomDto dto);
    @IterableMapping(qualifiedByName = "roomToRoomDto")
    List<RoomDto> allToDTO(List<Room> rooms);
}
