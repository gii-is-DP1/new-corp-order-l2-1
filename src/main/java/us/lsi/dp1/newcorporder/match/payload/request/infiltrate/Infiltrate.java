package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "consultant", defaultImpl = DefaultInfiltrate.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DefaultInfiltrate.class, name = "none"),
    @JsonSubTypes.Type(value = MediaAdvisorInfiltrate.class, name = "MEDIA_ADVISOR"),
    @JsonSubTypes.Type(value = CorporateLawyerInfiltrate.class, name = "CORPORATE_LAWYER"),
})
public interface Infiltrate {

    @JsonIgnore
    int getTotalNumberOfShares();

    void apply(Match match, UseConsultantRequest useConsultantRequests);

}
