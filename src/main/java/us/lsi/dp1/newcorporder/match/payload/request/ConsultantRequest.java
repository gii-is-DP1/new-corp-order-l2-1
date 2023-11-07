package us.lsi.dp1.newcorporder.match.payload.request;

import lombok.Data;
import us.lsi.dp1.newcorporder.match.ConsultantType;

import javax.annotation.Nullable;

@Data
public class ConsultantRequest {
    @Nullable private ConsultantType consultant;
}
