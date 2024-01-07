import {FrontendState} from "../FrontendState";
import {consultant} from "../../../data/MatchEnums";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";

function MediaAdvisorConglomeratePicker() {
        const context = useContext(StateContext);
        return pickOneCard(context.hand.components, (selected) => {
            context.state.infiltrate.mediaAdvisor.conglomerate = context.hand.values[selected[0]];
            context.update();
        })
    }

export class MediaAdvisorConglomerateState extends FrontendState {
    component = <MediaAdvisorConglomeratePicker/>

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.mediaAdvisor.conglomerate !== null)
            return frontendState.DONE;
    }
}
