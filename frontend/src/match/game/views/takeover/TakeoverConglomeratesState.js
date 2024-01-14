import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickManyConglomeratesOfTheSameColor} from "../../selector/pickers/Pickers";
import {Info} from "../../../Match";

function TakeoverConglomeratesPicker() {
    const context = useContext(StateContext);
    const info = useContext(Info);
    const state = context.state;
    return pickManyConglomeratesOfTheSameColor(context.nonrotatedHqConglomerates.components, (selected) => {
        const conglomerates = selected.map(index => context.nonrotatedHqConglomerates.values[index]);
        state.takeover.conglomerates = {agents: conglomerates.length, type: conglomerates[0]}
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
