package us.lsi.dp1.newcorporder.match.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.payload.CompanyTileReference;
import us.lsi.dp1.newcorporder.match.turn.*;

@Data
@Builder
public class TurnView {

    public static TurnView of(TurnSystem turnSystem) {
        Turn turn = turnSystem.getCurrentTurn();

        TurnView view = TurnView.builder()
            .finalRound(turnSystem.isFinalRound())
            .player(turnSystem.getCurrentPlayer() != null ? turnSystem.getCurrentPlayer().getPlayerId() : null)
            .action(turn == null ? null : turn.getAction())
            .state(turn == null ? null : turn.getState())
            .usingConsultant(turn == null ? null : turn.getChosenConsultant())
            .build();

        if (turn instanceof TakeOverTurn takeOverTurn) {
            view.setTakeOverProperties(takeOverTurn);
        }

        return view;
    }

    private final boolean finalRound;
    private final Integer player;
    private final Action action;
    private final TurnState state;

    private final ConsultantType usingConsultant;
    private CompanyTileReference targetCompany;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public CompanyTileReference getTargetCompany() {
        return targetCompany;
    }

    private void setTakeOverProperties(TakeOverTurn turn) {
        if (turn.getTakeOverRequest() != null) {
            this.targetCompany = turn.getTakeOverRequest().getTargetCompany();
        }
    }
}
