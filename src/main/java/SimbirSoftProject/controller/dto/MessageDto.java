package SimbirSoftProject.controller.dto;

import SimbirSoftProject.entity.Messages;
import SimbirSoftProject.entity.Room;
import SimbirSoftProject.entity.User;
import SimbirSoftProject.mapper.MessageMapper;
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
    public String content;
    private User toId;
    private User fromId;
    private Room roomId;

    public Messages messagesDtoTomMessage(MessageDto messageDto){
        return MessageMapper.MESSAGE_MAPPER.messagesDtoToMessages(messageDto);
    }
}
