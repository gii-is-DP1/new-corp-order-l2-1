package us.lsi.dp1.newcorporder.match.payload.request.infiltrate;

import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.payload.request.ConsultantRequest;

public interface Infiltrate {
    void infiltrate(Match match, ConsultantRequest consultantRequests);
}
