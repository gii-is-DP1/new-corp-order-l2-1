import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {OpponentConglomerateViewer} from "../../viewers/OpponentConglomerateViewer";
import {pickOneCard} from "../../selector/pickers/Pickers";


function GuerrillaMarketingOpponentPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const opponents = [];
    for (let i = 0; i < state.game.opponents.length; i++) {
        opponents[i] = <OpponentConglomerateViewer opponent={state.game.opponents[i]} key={i}/>;
    }
    return pickOneCard(opponents, (selected) => {
        state.takeover.ability.guerrillaMarketing.opponent = opponents[selected[0]];
        context.update();
    })

}

export class GuerrillaMarketingOpponentState extends FrontendState {
    component = <GuerrillaMarketingOpponentPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.guerrillaMarketing.opponent !== null)
            return frontendState.takeover.GUERRILLA_MARKETING_PICK_CONGLOMERATES;
    }
}
