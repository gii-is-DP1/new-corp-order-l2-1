import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";

function InfiltrateNewConsultantDrawer(){
    const context= useContext(StateContext);
    const state = context.state;

    return pickOneCard(state.generalSupplyConsultants.components, (selected) => {
        const index = selected[0];
        state.infiltrate.consultant = state.generalSupplyConsultants.values[index];
        context.update();
    })
}

export class InfiltrateNewConsultantState extends FrontendState {
    component = <InfiltrateNewConsultantDrawer/>

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.takenConsultant !== null)
                    return frontendState.DONE;
    }
}
