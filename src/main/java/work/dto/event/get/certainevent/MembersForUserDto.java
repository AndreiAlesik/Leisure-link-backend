package work.dto.event.get.certainevent;

import java.util.UUID;

import work.domain.AppMemberType;

public record MembersForUserDto(UUID id, String name, String lastName, AppMemberType type) {}
