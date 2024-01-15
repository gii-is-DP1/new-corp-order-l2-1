import {FrontendState} from "./FrontendState";
import React, {useContext} from "react";
import tokenService from "../../../services/token.service";
import {CompanyMatrixViewer} from "../viewers/CompanyMatrixViewer";
import {StateContext} from "../Game";
import {Info} from "../../Match";

function WaitingForTurn() {
    return <>
        <h2>Waiting for turn...</h2>
        <CompanyMatrixViewer/>
    </>
}

export class WaitingForTurnState extends FrontendState {
    component = <WaitingForTurn/>
    getNextState(gameState, frontendState) {
        if (gameState.isPlaying)
            return frontendState.CHOOSE_ACTION;
    }
}
