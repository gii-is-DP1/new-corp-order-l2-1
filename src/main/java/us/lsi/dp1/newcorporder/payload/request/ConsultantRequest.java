package us.lsi.dp1.newcorporder.payload.request;

import lombok.Data;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.ConsultantType;

import javax.annotation.Nullable;

@Data
public abstract class ConsultantRequest {

    @Nullable
    private ConsultantType consultant;
}
