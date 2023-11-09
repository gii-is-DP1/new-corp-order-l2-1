package us.lsi.dp1.newcorporder.match.payload.response;

import lombok.NoArgsConstructor;
import us.lsi.dp1.newcorporder.match.turn.TurnState;

@NoArgsConstructor
public class InfiltrateResponse extends TurnStateResponse {

    public InfiltrateResponse(TurnState nextState) {
        super(nextState);
    }

}
