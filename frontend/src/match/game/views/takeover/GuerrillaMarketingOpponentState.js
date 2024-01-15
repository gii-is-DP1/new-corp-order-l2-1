import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";

function GuerrillaMarketingOpponentPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const components = context.opponents.values.map(item => <p>{item.username}</p>);
    return pickOneCard(components, (selected) => {
        state.takeover.ability.guerrillaMarketing.opponent = context.opponents.values[selected[0]];
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
