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
        if ((gameState.infiltrate.conglomerateQuantity + (gameState.infiltrate.extraConglomerate != null ? 1 : 0) >= 3)
            && gameState.infiltrate.takenConsultant === null){
            return frontendState.infiltrate.TAKE_CONSULTANT;
        }

        if (!gameState.isPlaying) {
            return frontendState.WAITING_FOR_TURN;
        }

        console.log(gameState.game.player.hand.GENERIC_INC
            + gameState.game.player.hand.MEGAMEDIA
            + gameState.game.player.hand.OMNICORP
            + gameState.game.player.hand.TOTAL_ENTERTAINMENT)
        if(gameState.game.player.hand.GENERIC_INC
            + gameState.game.player.hand.MEGAMEDIA
            + gameState.game.player.hand.OMNICORP
            + gameState.game.player.hand.TOTAL_ENTERTAINMENT
            > 6)
            return frontendState.DISCARD;
    }
}
