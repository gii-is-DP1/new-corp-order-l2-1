package us.lsi.dp1.newcorporder.match.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.lsi.dp1.newcorporder.match.ConsultantType;

import javax.annotation.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TakeConsultantRequest {
    @Nullable private ConsultantType consultant;
}
