package server.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotEmpty(message = "Login mustn't be empty")
    @NonNull
    private String login;

    @Size(min = 4, max = 16, message = "The password must be between 4 and 16 characters long")
    @NonNull
    private String password;

    public UserDTO(Long id, String login) {
        this.login = login;
        this.id = id;
    }
}
