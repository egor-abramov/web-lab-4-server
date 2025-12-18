package server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private Map<String, String> errors;

    public void addError(String error, String message) {
        errors.put(error, message);
    }
}
