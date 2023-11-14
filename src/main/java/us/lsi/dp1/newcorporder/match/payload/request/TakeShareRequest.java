package us.lsi.dp1.newcorporder.match.payload.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.Conglomerate;

@Data
@Builder
public class TakeShareRequest {

    public enum Source {DECK, OPEN_DISPLAY}

    @NotNull private Source source;
    @Nullable private Conglomerate conglomerate;

}
