package us.lsi.dp1.newcorporder.match.payload.response;

import com.google.common.collect.Multiset;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.match.Conglomerate;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class DiscardShareResponse extends TurnStateResponse {
    private Multiset<Conglomerate> openDisplay;
}
