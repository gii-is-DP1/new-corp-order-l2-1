package us.lsi.dp1.newcorporder.stats.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class GameMetricsResponse {

    private double averageDuration;
    private long minDuration;
    private long maxDuration;

    private double averageMaxPlayers;

    private double averagePlayedMatches;
    private long minPlayedMatches;
    private long maxPlayedMatches;

    private long timesPlotted;
    private long timesInfiltrated;
    private long timesTakenOver;

}
