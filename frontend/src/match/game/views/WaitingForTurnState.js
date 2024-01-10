import {FrontendState} from "./FrontendState";
import React, {useContext} from "react";
import tokenService from "../../../services/token.service";
import {CompanyMatrixViewer} from "../viewers/CompanyMatrixViewer";
import {StateContext} from "../Game";
import {Info} from "../../Match";

function WaitingForTurn() {
    const info = useContext(Info);
    const context = useContext(StateContext);
    return <>
        <h2>Waiting for turn...</h2>
        <p>Is {info.players[context.state.turn].username}'s turn</p>
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
