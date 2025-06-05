package work.domain;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class UserDetails {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(updatable = false, nullable = false)
  private UUID id;

  private String name;

  private String lastName;

  private String address;

  private String phoneNumber;

  private ZonedDateTime birthDate;

  private byte[] photo;

  @OneToOne(fetch = FetchType.LAZY)
  private User user;
}
