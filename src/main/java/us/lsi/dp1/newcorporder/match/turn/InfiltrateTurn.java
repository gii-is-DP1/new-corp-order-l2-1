package us.lsi.dp1.newcorporder.match.turn;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.payload.request.DiscardShareRequest;
import us.lsi.dp1.newcorporder.match.payload.request.InfiltrateRequest;
import us.lsi.dp1.newcorporder.match.payload.request.TakeConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.UseConsultantRequest;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.Infiltrate;
import us.lsi.dp1.newcorporder.match.payload.response.DiscardShareResponse;
import us.lsi.dp1.newcorporder.match.payload.response.InfiltrateResponse;
import us.lsi.dp1.newcorporder.match.payload.response.UseConsultantResponse;

@Getter
public class InfiltrateTurn extends Turn {

    public enum State implements TurnState {SELECTING_CONSULTANT, INFILTRATE, TAKING_CONSULTANT, NONE}

    private State state = InfiltrateTurn.State.SELECTING_CONSULTANT;
    private UseConsultantRequest useConsultantRequest;

    public InfiltrateTurn(Match match) {
        super(Action.INFILTRATE, match);
    }

    @Builder
    private InfiltrateTurn(Match match, State currentState, UseConsultantRequest useConsultantRequest) {
        this(match);
        this.state = currentState;
        this.useConsultantRequest = useConsultantRequest;
    }

    @Override
    public ConsultantType getChosenConsultant() {
        return this.useConsultantRequest.getConsultant();
    }

    @Override
    public UseConsultantResponse onUseConsultantRequest(UseConsultantRequest request) {
        checkState(State.SELECTING_CONSULTANT);
        long numDifferentConglomerates = turnSystem.getCurrentPlayer().getHand().entrySet().size();

        Preconditions.checkArgument(request.getConsultant() != ConsultantType.MEDIA_ADVISOR || numDifferentConglomerates > 1,
            "you cannot use the Consultant 'Media Advisor' if you only have one type of conglomerate share in hand");
        Preconditions.checkArgument(isValidConsultant(request.getConsultant()),
            "invalid consultant for an infiltrate turn");
        Preconditions.checkArgument(!(request.getConsultant() == ConsultantType.CORPORATE_LAWYER && match.getPlayers().size() == 2),
            "you cannot use the consultant 'Corporate lawyer' when playing with 2 players");
        Preconditions.checkArgument(!(request.getConsultant() == ConsultantType.MEDIA_ADVISOR && numDifferentConglomerates <= 1),
            "you cannot use the consultant 'Media Advisor' if you only have one type of conglomerate share in hand");

        this.useConsultantRequest = request;
        this.state = State.INFILTRATE;
        this.turnSystem.getCurrentPlayer().getHeadquarter().removeConsultant(request.getConsultant());
        this.turnSystem.getCurrentPlayer().addConsultantUse(request.getConsultant());

        return new UseConsultantResponse(state);
    }

    @Override
    public InfiltrateResponse onInfiltrateRequest(InfiltrateRequest request) {
        checkState(State.INFILTRATE);

        Infiltrate action = request.getAction();
        action.apply(match, useConsultantRequest);

        if (action.getTotalNumberOfShares() >= 3) {
            state = State.TAKING_CONSULTANT;
        } else {
            this.endTurn();
        }

        return new InfiltrateResponse(state);
    }

    @Override
    public void onTakeConsultantRequest(TakeConsultantRequest request) {
        checkState(State.TAKING_CONSULTANT);
        Preconditions.checkArgument(request.getConsultant() != useConsultantRequest.getConsultant(),
            "you cannot take the same consultant you used to infiltrate the company");

        match.getGeneralSupply().takeConsultant(request.getConsultant());
        turnSystem.getCurrentPlayer().getHeadquarter().addConsultant(request.getConsultant());
        this.endTurn();
    }

    @Override
    public DiscardShareResponse onDiscardShareRequest(DiscardShareRequest request) {
        throw new IllegalStateException("invalid move for the current action");
    }

    private void endTurn() {
        this.turnSystem.getCurrentPlayer().addTimeInfiltrated();
        this.state = State.NONE;
        turnSystem.passTurn();
    }

    private boolean isValidConsultant(ConsultantType consultant) {
        return consultant == null || consultant == ConsultantType.MEDIA_ADVISOR || consultant == ConsultantType.CORPORATE_LAWYER;
    }

    private void checkState(InfiltrateTurn.State state) {
        Preconditions.checkState(this.state == state, "invalid action for the current state (%s)", state);
    }

    // for testing purposes
    UseConsultantRequest getUseConsultantRequest() {
        return useConsultantRequest;
    }
}
