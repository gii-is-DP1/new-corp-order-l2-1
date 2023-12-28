import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {FrontendState} from "../FrontendState";
import {consultant} from "../../../data/MatchEnums";

function InfiltrateConsultantPicker() { //TODO: add possibility of not choosing consultant
    const context = useContext(StateContext);

    return pickOneCard(context.playerConsultants.components, (selected) => {
        const index = selected[0];
        context.state.infiltrate.consultant = context.playerConsultants.values[index];
        context.update();
    })
}

export class InfiltrateConsultantState extends FrontendState {
    component = <InfiltrateConsultantPicker/>;

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.consultant !== null)
            if (gameState.infiltrate.consultant === consultant.MEDIA_ADVISOR)
                return frontendState.infiltrate.MEDIA_ADVISOR_PICK_CONGLOMERATE
            else if (gameState.infiltrate.consultant === consultant.CORPORATE_LAWYER)
                return frontendState.infiltrate.CORPORATE_LAWYER_PICK_CONGLOMERATES
    }
}

