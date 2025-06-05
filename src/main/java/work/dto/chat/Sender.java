package work.dto.chat;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Sender {
  private UUID senderId;
  private String name;
  private String lastname;
}
