package us.lsi.dp1.newcorporder.match.payload.response;

import com.google.common.collect.Multiset;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;

@Data
@AllArgsConstructor
public class DiscardShareResponse {
    private Multiset<Conglomerate> openDisplay;
}
