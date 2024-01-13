import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickManyConglomeratesOfTheSameColor} from "../../selector/pickers/Pickers";
import {startingState as state} from "../../../data/MockupData";
import {FrontendState} from "../FrontendState";
import {getConglomerateName} from "../../../data/MatchEnums";

export class InfiltrateConglomeratesState extends FrontendState {
    component = <InfiltrateConglomeratesPicker/>
    getNextState(gameState, frontendState) {
        if (state.infiltrate.conglomerate !== null)
            return frontendState.infiltrate.PICK_COMPANY;
    }
}

function InfiltrateConglomeratesPicker() {
    const context = useContext(StateContext);
    console.log("CONGLOMERATE STATES")
    return pickManyConglomeratesOfTheSameColor(context.hand.components, (selected) => {
        context.state.infiltrate.conglomerate = getConglomerateName(context.hand.values[selected[0]]);
        context.state.infiltrate.conglomerateQuantity = selected.length;
        context.update();
    })
}
