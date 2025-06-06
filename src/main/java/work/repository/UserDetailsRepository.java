package work.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import work.domain.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {

  @Query(
      value =
          "select ud.* from user_details ud right join public.users u on u.id = ud.user_id where u.id=:userId",
      nativeQuery = true)
  Optional<UserDetails> findByUserId(UUID userId);

  @Transactional
  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Query(
      value = "update user_details set user_id=:userId where id=:userDetailsId",
      nativeQuery = true)
  void setUserDetailsId(@Param("userId") UUID userId, @Param("userDetailsId") UUID userDetailsId);

  @Query(
      value =
          "select ud.* from user_details ud right join public.users u on u.id = ud.user_id where u.id=:userId",
      nativeQuery = true)
  Optional<UserDetails> findDetailsByUserId(@Param("userId") UUID userId);
}
