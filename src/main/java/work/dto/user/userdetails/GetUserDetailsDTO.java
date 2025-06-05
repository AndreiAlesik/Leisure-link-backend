package work.dto.user.userdetails;

import java.time.ZonedDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class GetUserDetailsDTO {

  private String name;

  private String lastName;

  private String address;

  private String phoneNumber;

  private ZonedDateTime birthDate;

  private byte[] photo;
}
