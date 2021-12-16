package SimbirSoftProject.controller.dto;

import SimbirSoftProject.entity.Room;
import SimbirSoftProject.entity.RoomType;
import SimbirSoftProject.entity.User;
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
    private User userIdCreator;

    public Room roomDtoToRoom(RoomDto roomDto){
        Room room = new Room();
        room.setName(roomDto.getName());
        room.setType(room.getType());
        room.setUser_id_creator(room.getUser_id_creator());
        return room;
    }

}
