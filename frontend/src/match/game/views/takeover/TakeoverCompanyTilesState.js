import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOrthogonallyAdjacentCompanyTiles} from "../../selector/pickers/Pickers";

 function TakeoverCompanyTilesPicker(){
    const context= useContext(StateContext);
    const state = context.state;

    return pickOrthogonallyAdjacentCompanyTiles(context.companyTiles.components, (selected) => {
        state.takeover.companyTiles = selected.map(index => state.game.companies[index]);
        context.update();
    })
}

export class TakeoverCompanyTilesState extends FrontendState {
    component = <TakeoverCompanyTilesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.companyTiles !== null)
            return frontendState.takeover.ACTIVATE_ABILITY;
    }
}
