package us.lsi.dp1.newcorporder.match.payload.request;

import lombok.Data;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;

import javax.annotation.Nullable;

@Data
public class UseConsultantRequest {
    @Nullable private ConsultantType consultant;
}
