package us.lsi.dp1.newcorporder.match.payload.response;

import com.google.common.collect.Multiset;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TakeShareResponse extends TurnStateResponse {

    private Conglomerate shareTaken;
    private Multiset<Conglomerate> openDisplay;

}
