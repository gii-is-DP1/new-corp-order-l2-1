package us.lsi.dp1.newcorporder.match.payload.request;

import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.ConsultantType;

import javax.annotation.Nullable;

@Data
@Builder
public class UseConsultantRequest {
    @Nullable private ConsultantType consultant;
}
