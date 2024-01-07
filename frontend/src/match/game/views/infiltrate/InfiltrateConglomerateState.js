import {PlotFirstConglomeratePicker} from "../plot/PlotFirstConglomeratePicker";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickManyConglomeratesOfTheSameColor} from "../../selector/pickers/Pickers";
import {startingState as state} from "../../../data/MockupData";
import {FrontendState} from "../FrontendState";

export class InfiltrateConglomeratesState extends FrontendState {
    component = <InfiltrateConglomeratesPicker/>
    getNextState(gameState, frontendState) {
        if (state.infiltrate.conglomerates !== null)
            return frontendState.infiltrate.PICK_CONSULTANT
    }
}

function InfiltrateConglomeratesPicker() {
    const context = useContext(StateContext);

    return pickManyConglomeratesOfTheSameColor(context.hand.components, (selected) => {
        context.state.infiltrate.conglomerates = selected.map(index => context.hand.values[index]);
        context.update();
    })
}
