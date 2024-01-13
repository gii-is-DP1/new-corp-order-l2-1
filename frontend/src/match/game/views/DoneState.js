import {FrontendState} from "./FrontendState";
import React, {useContext} from "react";
import {Info} from "../../Match";
import {StateContext} from "../Game";

function Done() {
    return <p>Done</p>
}

export class DoneState extends FrontendState {
    component = <Done/>

    getNextState(gameState, frontendState) {
        console.log(gameState);
        if (gameState.infiltrate.consultant >= 3 && gameState.infiltrate.takenConsultant === null)
            return frontendState.infiltrate.TAKE_CONSULTANT;
        console.log(gameState.isPlaying)
        if (!gameState.isPlaying) {
            return frontendState.WAITING_FOR_TURN;
        }
    }
}
