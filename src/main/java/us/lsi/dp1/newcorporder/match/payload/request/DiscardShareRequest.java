package us.lsi.dp1.newcorporder.match.payload.request;

import com.google.common.collect.Multiset;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;

@Data
public class DiscardShareRequest {

    @NotNull private Multiset<Conglomerate> sharesToDiscard;
}
