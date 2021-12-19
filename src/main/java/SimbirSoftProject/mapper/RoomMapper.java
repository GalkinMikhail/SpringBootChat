package SimbirSoftProject.mapper;

import SimbirSoftProject.controller.dto.RoomDto;
import SimbirSoftProject.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomMapper ROOM_MAPPER = Mappers.getMapper(RoomMapper.class);
    RoomDto roomToRoomDto(Room entity);
    Room roomDtoToRoom(RoomDto dto);
    List<RoomDto> allToDTO(List<Room> rooms);
}
