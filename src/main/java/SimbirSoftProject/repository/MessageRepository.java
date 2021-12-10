package SimbirSoftProject.repository;

import SimbirSoftProject.domain.util.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages,Long> {
}
