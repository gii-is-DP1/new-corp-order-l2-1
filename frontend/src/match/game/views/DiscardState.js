import {FrontendState} from "./FrontendState";
import React, {useContext} from "react";
import {StateContext} from "../Game";
import {pickNCards} from "../selector/pickers/Pickers";
import {getConglomerateName} from "../../data/MatchEnums";
import fetchAuthenticatedWithBody from "../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../Match";

function Discard() {
    const context = useContext(StateContext);
    const info = useContext(Info);
    const cardsToPick = context.hand.values.length - 6;
    return pickNCards("Discard " + cardsToPick + " conglomerates", context.hand.components, selected => {
            const oldHand = {...context.state.game.player.hand};
            selected.map(i => context.state.game.player.hand[getConglomerateName(context.hand.values[i])]--);
            context.update();

            const hand = context.state.game.player.hand;
            const body = {
                sharesToDiscard: {
                    OMNICORP: oldHand.OMNICORP - hand.OMNICORP,
                    TOTAL_ENTERTAINMENT: oldHand.TOTAL_ENTERTAINMENT - hand.TOTAL_ENTERTAINMENT,
                    GENERIC_INC: oldHand.GENERIC_INC - hand.GENERIC_INC,
                    MEGAMEDIA: oldHand.MEGAMEDIA - hand.MEGAMEDIA,
                }
            }

            const infiltrateFetch = async () => {
                try {
                    await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/shares`, "DELETE", body)
                        .then(async response => await response.json());
                } catch (error) {
                    console.log(error.message)
                }
            };
            infiltrateFetch();
        },
        cardsToPick)
}

export class DiscardState extends FrontendState {
    component = <Discard/>

    getNextState(gameState, frontendState) {
        if (gameState.game.player.hand.GENERIC_INC
            + gameState.game.player.hand.MEGAMEDIA
            + gameState.game.player.hand.OMNICORP
            + gameState.game.player.hand.TOTAL_ENTERTAINMENT <= 6)
            return frontendState.DONE;
    }
}
