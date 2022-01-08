package SimbirSoftProject.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    private String name;

//    @ManyToMany(mappedBy = "role")
//    private List<User> users;

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}
