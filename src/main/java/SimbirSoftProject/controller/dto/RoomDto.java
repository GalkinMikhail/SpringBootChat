package SimbirSoftProject.controller.dto;

import SimbirSoftProject.entity.Room;
import SimbirSoftProject.entity.RoomType;
import SimbirSoftProject.entity.User;
import SimbirSoftProject.mapper.RoomMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    private RoomType type = RoomType.PUBLIC;
    private User creatorId;

    public Room roomDtoToRoom(RoomDto roomDto){
        return RoomMapper.ROOM_MAPPER.roomDtoToRoom(roomDto);
    }

}
