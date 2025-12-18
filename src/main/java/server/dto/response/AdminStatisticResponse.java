package server.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class AdminStatisticResponse {
    private final Map<String, Float> statistic = new HashMap<>();

    public void addUserStatistic(UserDTO user, Float hitRate) {
        statistic.put(user.getLogin(), hitRate);
    }
}
