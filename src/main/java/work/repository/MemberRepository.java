package work.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import work.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
  @Query(
      value = "select * from members where event_id=:eventId and type='ROLE_HOST'",
      nativeQuery = true)
  Optional<Member> findEventHost(@Param("eventId") UUID eventId);

  @Query(
      value =
          "select * from public.members m join events e on e.id = m.event_id where e.id=:eventId and m.user_id=:userId",
      nativeQuery = true)
  Optional<Member> isMemberExistInEvent(
      @Param("userId") UUID userId, @Param("eventId") UUID eventId);

  @Query(
      value = "select * from members where user_id=:userId and event_id=:eventId",
      nativeQuery = true)
  Optional<Member> findMemberByUserIdAndEventId(
      @Param("userId") UUID userId, @Param("eventId") UUID eventId);
}
