package chat.repository;

import chat.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages,Long> {
}
