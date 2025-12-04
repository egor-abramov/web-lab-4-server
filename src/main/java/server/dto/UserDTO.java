package server.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotEmpty(message = "Login mustn't be empty")
    private String login;

    @Size(min = 4, max = 16, message = "The password must be between 4 and 16 characters long")
    private String password;
}
