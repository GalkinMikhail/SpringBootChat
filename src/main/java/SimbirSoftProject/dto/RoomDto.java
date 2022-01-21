package SimbirSoftProject.dto;

import SimbirSoftProject.mapper.RoomMapper;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RoomDto {
    private String name;
    private RoomType type;
    public Room roomDtoToRoom(RoomDto roomDto){
        return RoomMapper.ROOM_MAPPER.roomDtoToRoom(roomDto);
    }

}
