package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.ability.AmbientAdvertisingAbility;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BasicInfiltrate.class),
    @JsonSubTypes.Type(value = MediaAdvisorInfiltrate.class),
    @JsonSubTypes.Type(value = AmbientAdvertisingAbility.class),
})
public interface Infiltrate {

    @JsonIgnore
    int getTotalNumberOfShares();

    void run(Match match, ConsultantRequest consultantRequests);

}
