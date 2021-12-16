package SimbirSoftProject.controller.dto;

import SimbirSoftProject.entity.Messages;
import SimbirSoftProject.entity.Room;
import SimbirSoftProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private long id;
    public Date created_at;
    public Date updatedAt;
    public String content;
    private User toId;
    private User fromId;
    private Room roomId;

    public Messages messagesDtoTomMessage(MessageDto messageDto){
        Messages messages = new Messages();
        messages.setContent(messages.getContent());
        messages.setCreated_at(messages.getCreated_at());
        messages.setUpdated_at(messages.getUpdated_at());
        messages.setTo_id(messages.getTo_id());
        messages.setFrom_id(messages.getFrom_id());
        messages.setRoom_id(messages.getRoom_id());
        return messages;
    }
}
