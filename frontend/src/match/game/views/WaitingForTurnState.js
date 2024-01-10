import {FrontendState} from "./FrontendState";
import React from "react";
import tokenService from "../../../services/token.service";
import {CompanyMatrixViewer} from "../viewers/CompanyMatrixViewer";

function WaitingForTurn() {
    return <>
        <h2>Waiting for turn...</h2>
        <CompanyMatrixViewer/>
    </>
}

export class WaitingForTurnState extends FrontendState {
    component = <WaitingForTurn/>

    getNextState(gameState, frontendState) {
        if (gameState.turn === tokenService.getUser().id)
            return frontendState.CHOOSE_ACTION;
    }
}
