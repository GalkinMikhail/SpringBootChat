package SimbirSoftProject.domain.dto;

import SimbirSoftProject.domain.util.Contents;
import SimbirSoftProject.domain.util.Room;
import SimbirSoftProject.domain.util.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private long id;
    public Date createdAt;
    public Date updatedAt;
    private Contents content;
    private User toId;
    private User fromId;
    private Room roomId;
}
