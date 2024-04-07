package work.web.geodata;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Tag(name = "Geodata", description = "Geodata API")
public interface GeodataController {
    @PermitAll
    @GetMapping("/autocomplete/{input}")
    Mono<String> getAddressAutocomplete(@PathVariable("input") String input);
}
