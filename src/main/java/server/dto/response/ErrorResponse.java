package server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private final Map<String, String> errors = new HashMap<>();

    public void addError(String error, String message) {
        errors.put(error, message);
    }
}
