package us.lsi.dp1.newcorporder.match.payload.request;

import com.google.common.collect.Multiset;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscardShareRequest {

    @NotNull private Multiset<Conglomerate> sharesToDiscard;
}
