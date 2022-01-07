package SimbirSoftProject.dto;

import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.RoomType;
import SimbirSoftProject.model.User;
import SimbirSoftProject.mapper.RoomMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private String name;
    private RoomType type;
    public Room roomDtoToRoom(RoomDto roomDto){
        return RoomMapper.ROOM_MAPPER.roomDtoToRoom(roomDto);
    }

}
