package SimbirSoftProject.dto;

import SimbirSoftProject.model.Messages;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.User;
import SimbirSoftProject.mapper.MessageMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class MessageDto {
    private Date createdAt = new Date();
    private String content;
    private String roomName;


    public Messages messagesDtoTomMessage(MessageDto messageDto){
        return MessageMapper.MESSAGE_MAPPER.messagesDtoToMessages(messageDto);
    }
}
