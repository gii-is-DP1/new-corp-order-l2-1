import {FrontendState} from "../FrontendState";
import {AbilityChooser} from "./ActivateAbilityState";

function DrawTwoCardsFromDeck() {
    return <></>
}

export class DealmakerDrawTwoCardsFromDeckState extends FrontendState {
    component = <DrawTwoCardsFromDeck/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.dealmaker.conglomerates.length === 2)
            return frontendState.DONE;
    }
}
