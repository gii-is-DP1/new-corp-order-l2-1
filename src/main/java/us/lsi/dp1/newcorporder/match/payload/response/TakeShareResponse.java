package us.lsi.dp1.newcorporder.match.payload.response;

import com.google.common.collect.Multiset;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.turn.PlotTurn;

@Data
@Builder
public class TakeShareResponse {

    private Conglomerate shareTaken;
    private PlotTurn.State nextState;
    private Multiset<Conglomerate> openDisplay;

}
