package us.lsi.dp1.newcorporder.match.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.turn.TurnState;

@Data
@AllArgsConstructor
public class UseConsultantResponse {
    private TurnState nextState;
}
