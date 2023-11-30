package us.lsi.dp1.newcorporder.match.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.Infiltrate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfiltrateRequest {
    @NotNull Infiltrate action;
}
