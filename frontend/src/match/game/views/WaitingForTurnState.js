import {FrontendState} from "./FrontendState";
import React from "react";
import tokenService from "../../../services/token.service";

function WaitingForTurn() {
    return <p>Waiting for turn...</p>
}

export class WaitingForTurnState extends FrontendState {
    component = <WaitingForTurn/>

    getNextState(gameState, frontendState) {
        if (gameState.turn === tokenService.getUser().id)
            return frontendState.CHOOSE_ACTION;
    }
}
