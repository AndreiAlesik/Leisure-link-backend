package work.service.event;

import work.dto.ResponseObject;
import work.dto.event.create.EventCreateDto;
import work.dto.event.get.EventsInRadiusDto;
import work.dto.event.get.SearchEventDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    ResponseObject createEvent(HttpServletRequest request, EventCreateDto eventToCreate);

    List<EventsInRadiusDto> getEventsWithinRadius(HttpServletRequest request, SearchEventDTO searchEventDTO);
}
