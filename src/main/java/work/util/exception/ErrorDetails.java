package work.util.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
  private HttpStatus code;
  private String message;
  private String token;

  public ErrorDetails(HttpStatus code, String message, String token) {
    this.code = code;
    this.message = message;
    this.token = token;
  }
}
