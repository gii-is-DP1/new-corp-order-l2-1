import {FrontendState} from "../FrontendState";
import {consultant} from "../../../data/MatchEnums";

export function AbilityChooser() { //TODO:implement component

}

export class TakeoverCompanyTilesState extends FrontendState {
    component = <AbilityChooser/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.choice !== null)
            if (gameState.takeover.consultant === consultant.DEALMAKER)
                return frontendState.takeover.DEALMAKER_DRAW_TWO_CARDS_FROM_DECK
        return frontendState.DONE //TODO: link here company abilites
    }
}
