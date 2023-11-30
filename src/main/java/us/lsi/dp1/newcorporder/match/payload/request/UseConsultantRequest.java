package us.lsi.dp1.newcorporder.match.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.lsi.dp1.newcorporder.match.ConsultantType;

import javax.annotation.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UseConsultantRequest {
    @Nullable private ConsultantType consultant;
}
