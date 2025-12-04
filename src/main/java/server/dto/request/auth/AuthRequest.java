package server.dto.request.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
abstract public class AuthRequest {
    @Size(min = 1, max = 50, message = "The login must be between 1 and 50 characters long")
    @NotEmpty(message = "Login mustn't be empty")
    private String login;

    @Size(min = 4, max = 16, message = "The password must be between 4 and 16 characters long")
    @NotEmpty(message = "Password mustn't be empty")
    private String password;
}
