package SimbirSoftProject.domain.util;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "Contents")
public class Contents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10000, message = "Message too long")
    @Column(name = "content")
    private String content;

    public Contents(){

    }
}
