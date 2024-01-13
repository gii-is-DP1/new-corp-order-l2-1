import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";


function PrintMediaOtherHqPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    return pickOneCard(context.opponents.components, (selected) => {
        state.takeover.ability.printMedia.otherHq = context.opponents.values[selected[0]].hq;
        context.update();
    })

}

export class PrintMediaOtherHqState extends FrontendState {
    component = <PrintMediaOtherHqPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.printMedia.otherHq !== null)
            return frontendState.takeover.PRINT_MEDIA_PICK_OTHER_CONGLOMERATE;
    }
}
