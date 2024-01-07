import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {optionallyPickCard} from "../../selector/pickers/Pickers";

function TakeoverConsultantPicker(){
    const context= useContext(StateContext);
    const state = context.state;
    return optionallyPickCard(context.playerConsultants.components, (selected) => {
        if (selected[0] == null)
            state.takeover.consultant = -1;
        else {
            const index = selected[0];
            state.takeover.consultant = context.playerConsultants.values[index];
        }
        context.update();
    })
}

export class TakeoverConsultantState extends FrontendState {
    component = <TakeoverConsultantPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.consultant !== null)
                    return frontendState.takeover.PICK_CONGLOMERATES;
    }
}
