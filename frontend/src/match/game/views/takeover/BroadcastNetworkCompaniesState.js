import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneOrTwoCompanies} from "../../selector/pickers/Pickers";

function BroadcastNetworkCompaniesPicker(){
    const context= useContext(StateContext);
    const state = context.state;
    //TODO ADD SAME COLOR CHECK FOR SOURCE TO DEST
    //TODO CHECK SAME COLOR PICKER FOR CONGLOMERATE USE ONLY
    return pickOneOrTwoCompanies(context.companyTiles.components, (selected) => {
        state.takeover.ability.broadcastNetwork.companies = selected.map(index => state.game.companies[index]);
        if(selected.length === 1)
            for (let i = 1; i < state.takeover.companyTiles[1].agents && i < 3 ; i++){
                state.takeover.companyTiles[1].agents--;
                state.game.companies[selected[0]].agents++;
            }
        if(selected.length === 2) {
            if (state.takeover.companyTiles[1].agents > 1) {
                state.takeover.companyTiles[1].agents--;
                state.game.companies[selected[0]].agents++;
            }
            if (state.takeover.companyTiles[1].agents > 1) {
                state.takeover.companyTiles[1].agents--;
                state.game.companies[selected[1]].agents++;
            }
        }
        context.update();
    })
}

export class BroadcastNetworkCompaniesState extends FrontendState {
    component = <BroadcastNetworkCompaniesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.broadcastNetwork.companies !== null)
            return frontendState.DONE;
    }
}
