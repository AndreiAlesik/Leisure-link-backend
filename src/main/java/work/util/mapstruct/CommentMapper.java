package work.util.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;

import work.domain.Comment;
import work.dto.event.create.CreateCommentDto;
import work.dto.event.get.certainevent.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {
  Comment fromCreateCommentDto(CreateCommentDto createCommentDto);

  CommentDto toCommentDto(Comment comment);

  List<CommentDto> toCommentDtoList(List<Comment> commentsDto);
}
