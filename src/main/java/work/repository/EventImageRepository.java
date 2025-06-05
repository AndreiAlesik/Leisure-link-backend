package work.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import work.domain.EventImage;

@Repository
public interface EventImageRepository extends JpaRepository<EventImage, UUID> {}
