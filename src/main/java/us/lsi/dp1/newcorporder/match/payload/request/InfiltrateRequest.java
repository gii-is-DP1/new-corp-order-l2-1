package us.lsi.dp1.newcorporder.match.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.Infiltrate;

@Data
@Builder
public class InfiltrateRequest {
    @NotNull Infiltrate infiltrate;
}
