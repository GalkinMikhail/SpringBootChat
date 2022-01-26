package SimbirSoftProject.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "room")
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 30, message = "Room name is too long")
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id",nullable = false)
    private User creatorId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "room_participants",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> participants = new HashSet<>();
}
