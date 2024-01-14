import {FrontendState} from "./FrontendState";
import React, {useContext} from "react";
import {Info} from "../../Match";
import {StateContext} from "../Game";

function Done() {
    return <p>Done</p>
}

export class DoneState extends FrontendState {
    component = <Done/>

    getNextState(gameState, frontendState){
        console.log(gameState.infiltrate.takenConsultant);
        if ((gameState.infiltrate.conglomerateQuantity + gameState.infiltrate.extraConglomerate != null ? 1 : 0 >= 3)
            && gameState.infiltrate.takenConsultant === null){
            return frontendState.infiltrate.TAKE_CONSULTANT;
        }

        if (!gameState.isPlaying) {
            return frontendState.WAITING_FOR_TURN;
        }
    }
}
