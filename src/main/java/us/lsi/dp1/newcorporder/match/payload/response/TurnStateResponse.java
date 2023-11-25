package us.lsi.dp1.newcorporder.match.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.match.turn.TurnState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TurnStateResponse {
    private TurnState nextState;
}
