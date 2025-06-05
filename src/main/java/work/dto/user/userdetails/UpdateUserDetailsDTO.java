package work.dto.user.userdetails;

import java.time.ZonedDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class UpdateUserDetailsDTO {
  private String name;

  private String lastName;

  private String address;

  private String phoneNumber;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime birthDate;
}
