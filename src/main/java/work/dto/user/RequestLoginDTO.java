package work.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RequestLoginDTO(
        @NotNull
        @Email
        @NotEmpty(message = "EMAIL_MUST_BY_NOT_EMPTY")
        @Schema(description = "Email of an account.", example = "leisure@gmail.com")
        String email,

        @NotNull
        @NotEmpty(message = "PASSWORD_MUST_BY_NOT_EMPTY")
        @Schema(description = "Password of an account.", example = "123456789")
        String password
) {
}
