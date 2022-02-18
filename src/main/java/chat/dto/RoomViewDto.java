package chat.dto;

import chat.model.RoomType;
import chat.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomViewDto {
    private String name;
    private User owner;
    private RoomType type;
    private Set<User> participants;
}
