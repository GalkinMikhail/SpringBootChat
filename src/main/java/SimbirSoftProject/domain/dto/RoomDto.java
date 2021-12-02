package SimbirSoftProject.domain.dto;

import SimbirSoftProject.domain.util.RoomType;
import SimbirSoftProject.domain.util.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    private Set<RoomType> type;
    private User userIdCreator;
}
