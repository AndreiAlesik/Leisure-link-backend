package work.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PasswordResetDTO {
  @NotNull
  @Email
  @NotEmpty(message = "CODE_MUST_BY_NOT_EMPTY")
  @Schema(description = "code")
  private String code;

  @NotNull
  @Email
  @NotEmpty(message = "PASSWORD_MUST_BY_NOT_EMPTY")
  @Schema(description = "password")
  private String password;
}
