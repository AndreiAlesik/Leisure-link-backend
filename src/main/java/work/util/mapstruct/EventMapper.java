package work.util.mapstruct;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.*;

import work.domain.Event;
import work.dto.event.create.EventCreateDto;
import work.dto.event.get.EventsInRadiusDto;
import work.dto.event.get.certainevent.CertainEventDto;
import work.dto.event.get.search.EventDto;

@Mapper(componentModel = "spring")
public interface EventMapper {
  @Mapping(target = "categories", ignore = true)
  @Mapping(target = "location", source = "eventCreateDto", qualifiedByName = "toPoint")
  Event fromCreateDto(EventCreateDto eventCreateDto);

  @Mapping(target = "longitude", expression = "java(event.getLocation().getX())")
  @Mapping(target = "latitude", expression = "java(event.getLocation().getY())")
  @Mapping(target = "categories", ignore = true)
  EventsInRadiusDto eventToEventsInRadiusDto(Event event);

  @Named("toPoint")
  default Point toPoint(EventCreateDto eventCreateDto) {
    if (eventCreateDto == null
        || eventCreateDto.latitude() == null
        || eventCreateDto.longitude() == null) {
      return null;
    }
    GeometryFactory geometryFactory = new GeometryFactory();
    return geometryFactory.createPoint(
        new Coordinate(eventCreateDto.longitude(), eventCreateDto.latitude()));
  }

  @Mapping(target = "longitude", expression = "java(event.getLocation().getX())")
  @Mapping(target = "latitude", expression = "java(event.getLocation().getY())")
  @Mapping(target = "categories", ignore = true)
  CertainEventDto toCertainEventDto(Event event);

  @Mapping(target = "longitude", expression = "java(event.getLocation().getX())")
  @Mapping(target = "latitude", expression = "java(event.getLocation().getY())")
  @Mapping(target = "categories", ignore = true)
  EventDto eventToEventDto(Event event);
}
