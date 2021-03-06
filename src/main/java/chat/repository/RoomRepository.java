package chat.repository;

import chat.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {
    Optional<Room> findById(Long id);
    Optional<Room> findByName(String name);

}
