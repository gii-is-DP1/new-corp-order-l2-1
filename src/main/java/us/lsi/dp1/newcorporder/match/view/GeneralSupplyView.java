package us.lsi.dp1.newcorporder.match.view;

import com.google.common.collect.Multiset;
import lombok.Builder;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.GeneralSupply;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;

@Getter
@Builder
public class GeneralSupplyView {

    private final int deckSize;
    private final Multiset<ConsultantType> consultants;
    private final Multiset<Conglomerate> openDisplay;

    public static GeneralSupplyView of(GeneralSupply generalSupply) {
        return GeneralSupplyView.builder()
            .deckSize(generalSupply.getConglomerateSharesLeftInDeck())
            .consultants(generalSupply.getConsultantsLeft())
            .openDisplay(generalSupply.getOpenDisplay())
            .build();
    }
}
