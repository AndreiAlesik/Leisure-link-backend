package work.service.chat;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import work.dto.chat.MessageDTO;
import work.dto.chat.MessageGetDTO;

public interface ChatService {

  MessageDTO sendMessage(Principal principal, UUID eventId, MessageGetDTO messageDTO);

  List<MessageDTO> getHistory(Principal principal, UUID eventId);
}
