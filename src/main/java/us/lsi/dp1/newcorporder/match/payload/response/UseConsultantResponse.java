package us.lsi.dp1.newcorporder.match.payload.response;

import lombok.NoArgsConstructor;
import us.lsi.dp1.newcorporder.match.turn.TurnState;

@NoArgsConstructor
public class UseConsultantResponse extends TurnStateResponse {

    public UseConsultantResponse(TurnState nextState) {
        super(nextState);
    }
    
}
