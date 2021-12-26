package SimbirSoftProject.repository;


import SimbirSoftProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByLogin(String login);
}
