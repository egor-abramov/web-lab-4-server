package server.dto.request.point;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PointRequest {
    @NotNull(message = "X can't be null")
    private float x;

    @NotNull(message = "Y can't be null")
    private float y;

    @NotNull(message = "R can't be null")
    private float r;
}
