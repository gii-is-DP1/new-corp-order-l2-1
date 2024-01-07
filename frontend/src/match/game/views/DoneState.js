import {FrontendState} from "./FrontendState";
import React from "react";

function Done() {
    return <p>Done</p>
}

export class DoneState extends FrontendState {
    component = <Done/>

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.consultant >= 3 && gameState.infiltrate.takenConsultant === null)
            return frontendState.infiltrate.TAKE_CONSULTANT;
    }
}
