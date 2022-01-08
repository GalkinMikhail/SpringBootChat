package SimbirSoftProject.dto;

import SimbirSoftProject.mapper.MessageMapper;
import SimbirSoftProject.model.Messages;
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
