import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickManyConglomeratesOfTheSameColor} from "../../selector/pickers/Pickers";

function TakeoverConglomeratesPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    return pickManyConglomeratesOfTheSameColor(context.hand.components, (selected) => {
        state.takeover.conglomerates = selected.map(index => context.hand.values[index]);
        context.update();
    })
}

export class TakeoverConglomeratesState extends FrontendState {
    component = <TakeoverConglomeratesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.conglomerates !== null)
            return frontendState.takeover.PICK_TWO_COMPANY_TILES;
    }
}
