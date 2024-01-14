package us.lsi.dp1.newcorporder.match.payload.response;

import com.google.common.collect.Multiset;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TakeOverResponse extends TurnStateResponse {

    private Multiset<Conglomerate> hand;
    private Conglomerate sourceConglomerate;
}
