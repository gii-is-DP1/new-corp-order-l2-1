import {FrontendState} from "../FrontendState";
import {consultant} from "../../../data/MatchEnums";
import {AbilityChooser} from "./ActivateAbilityState";

function DrawTwoCardsFromDeck() { //TODO: implement component
    return <>Unimplemented component</>
}

export class DealmakerDrawTwoCardsFromDeckState extends FrontendState {
    component = <DrawTwoCardsFromDeck/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.dealmaker.conglomerates.length === 2)
            return frontendState.DONE;
    }
}
