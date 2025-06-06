package work.dto;

import org.springframework.http.HttpStatus;

import work.domain.AppUserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseObject {
  @Schema(description = "HTTP response", example = "OK/ACCEPTED/BAD_REQUEST and so on")
  private HttpStatus code;

  @Schema(description = "Response from backend", example = "Something went wrong/Successful")
  private String message;

  @Schema(description = "authentication token", example = "gfrekjn123kmfvkq")
  private String token;

  @Schema(description = "User role", example = "ROLE_USER")
  private AppUserRole appUserRole;

  public ResponseObject(HttpStatus code, String message, String token) {
    this.code = code;
    this.message = message;
    this.token = token;
  }

  public ResponseObject(HttpStatus code, String message, String token, AppUserRole appUserRole) {
    this.code = code;
    this.message = message;
    this.token = token;
    this.appUserRole = appUserRole;
  }
}
