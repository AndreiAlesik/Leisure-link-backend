package work.util.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import work.domain.Message;
import work.dto.chat.MessageDTO;
import work.dto.chat.MessageGetDTO;

@Mapper(componentModel = "spring")
public interface MessageMapper {
  @Mapping(source = "userId", target = "sender.senderId")
  MessageDTO toMessageDTO(Message message);

  @Mapping(source = "userId", target = "sender.senderId")
  List<MessageDTO> toListMessageDTO(List<Message> messages);

  Message fromMessageDTO(MessageDTO messageDTO);

  Message fromMessageGetDTO(MessageGetDTO messageGetDTO);
}
