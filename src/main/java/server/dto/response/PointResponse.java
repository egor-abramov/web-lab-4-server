package server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointResponse {
    private long id;
    private float x;
    private float y;
    private float r;
    private String time;
    private boolean isHit;

}
