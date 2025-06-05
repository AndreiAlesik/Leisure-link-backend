package work.util.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;

import work.domain.Member;
import work.dto.event.get.certainevent.MembersForUserDto;

@Mapper(componentModel = "spring")
public interface MemberMapper {
  List<MembersForUserDto> toMemberForUserDtoList(List<Member> members);
}
