import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickManyConglomeratesOfTheSameColor} from "../../selector/pickers/Pickers";
import {FrontendState} from "../FrontendState";

function CorporateLawyerConglomeratesPicker() {
    const context = useContext(StateContext);
    return pickManyConglomeratesOfTheSameColor(context.hand.components, (selected) => {
        context.state.infiltrate.corporateLawyer.conglomerates = {type: context.hand.values[selected[0]], quantity: selected.length}
        context.update();
    })
}

export class CorporateLawyerConglomeratesState extends FrontendState {
    component = <CorporateLawyerConglomeratesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.corporateLawyer.conglomerates !== null)
            return frontendState.infiltrate.CORPORATE_LAWYER_PICK_COMPANY;
    }
}
