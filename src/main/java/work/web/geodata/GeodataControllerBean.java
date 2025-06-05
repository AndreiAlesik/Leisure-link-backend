package work.web.geodata;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import work.service.geodata.GeodataService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/geodata", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@CrossOrigin
public class GeodataControllerBean implements GeodataController {

  private final GeodataService geodataService;

  @Override
  public Mono<String> getAddressAutocomplete(String input) {
    return geodataService.getAddressAutocomplete(input);
  }
}
