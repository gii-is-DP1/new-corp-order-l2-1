import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";


function AmbientAdvertisingOpponentPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    return pickOneCard(context.opponents.components, (selected) => {
        state.takeover.ability.ambientAdvertising.opponent = context.opponents.data[selected[0]];
        context.update();
    })

}

export class AmbientAdvertisingOpponentState extends FrontendState {
    component = <AmbientAdvertisingOpponentPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.ambientAdvertising.opponent !== null)
            return frontendState.takeover.AMBIENT_ADVERTISING_PICK_CONGLOMERATES;
    }
}
