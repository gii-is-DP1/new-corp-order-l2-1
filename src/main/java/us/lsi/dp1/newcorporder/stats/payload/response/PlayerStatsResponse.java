package us.lsi.dp1.newcorporder.stats.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class PlayerStatsResponse {

    private int totalMatches;
    private int wins;
    private int ties;
    private int loses;

}
