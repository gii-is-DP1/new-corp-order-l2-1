import {FrontendState} from "./FrontendState";
import React, {useContext} from "react";
import {Info} from "../../Match";
import {StateContext} from "../Game";

function Done() {
    return <p>Loading...</p>
}

export class DoneState extends FrontendState {
    component = <Done/>

    getNextState(gameState, frontendState){
        const canTakeConsultant = ((gameState.infiltrate.conglomerateQuantity + ((gameState.infiltrate.mediaAdvisor.conglomerate  === undefined || gameState.infiltrate.mediaAdvisor.conglomerate === null)  ? 0 : 1) >= 3)
            && gameState.infiltrate.takenConsultant === null);

        if (canTakeConsultant)
            return frontendState.infiltrate.TAKE_CONSULTANT;

        const conglomeratesInHand = gameState.game.player.hand.GENERIC_INC
            + gameState.game.player.hand.MEGAMEDIA
            + gameState.game.player.hand.OMNICORP
            + gameState.game.player.hand.TOTAL_ENTERTAINMENT;
        const maxConglomeratesInHand = 6;
        if(conglomeratesInHand > maxConglomeratesInHand)
            return frontendState.DISCARD;

        if (!gameState.isPlaying)
            return frontendState.WAITING_FOR_TURN;
    }
}
