package work.dto.chat;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MessageDTO {
  private UUID id;
  private String message;
  private ZonedDateTime createdDate;
  private Sender sender;
}
