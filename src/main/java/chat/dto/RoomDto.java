package chat.dto;

import chat.mapper.RoomMapper;
import chat.model.Room;
import chat.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDto {
    private String name;
    private RoomType type;
    public Room roomDtoToRoom(RoomDto roomDto){
        return RoomMapper.ROOM_MAPPER.roomDtoToRoom(roomDto);
    }

}
