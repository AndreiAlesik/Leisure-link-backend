package work.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import work.domain.Message;

public interface MessageRepository extends JpaRepository<Message, UUID> {

  @Query(value = "select * from message where event_id=:eventId", nativeQuery = true)
  List<Message> findByEventId(@Param("eventId") UUID eventId);
}
