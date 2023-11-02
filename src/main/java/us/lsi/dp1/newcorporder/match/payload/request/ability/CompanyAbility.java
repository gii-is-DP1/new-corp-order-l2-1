package us.lsi.dp1.newcorporder.match.payload.request.ability;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.TakeOverRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AmbientAdvertisingAbility.class),
    @JsonSubTypes.Type(value = BroadcastNetworkAbility.class),
    @JsonSubTypes.Type(value = GuerrillaMarketingAbility.class),
    @JsonSubTypes.Type(value = OnlineMarketingAbility.class),
    @JsonSubTypes.Type(value = PrintMediaAbility.class),
    @JsonSubTypes.Type(value = SocialMediaAbility.class)
})
public interface CompanyAbility {

    void activate(Match match, TakeOverRequest takeOverRequest);
}
