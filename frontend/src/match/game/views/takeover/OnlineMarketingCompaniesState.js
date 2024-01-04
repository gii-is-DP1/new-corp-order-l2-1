import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickTwoCompanies} from "../../selector/pickers/Pickers";

function OnlineMarketingCompaniesPicker(){
    const context= useContext(StateContext);
    const state = context.state;
    return pickTwoCompanies(context.companyTiles.components, (selected) => {
        state.takeover.ability.onlineMarketing.companies = selected.map(index => state.game.companies[index]);
        const firstCompanyType = state.game.companies[selected[0]].type;
        state.game.companies[selected[0]].type = state.game.companies[selected[1]].type;
        state.game.companies[selected[1]].type = firstCompanyType;
        const firstCompanyAgents = state.game.companies[selected[0]].agents;
        state.game.companies[selected[0]].agents = state.game.companies[selected[1]].agents;
        state.game.companies[selected[1]].agents = firstCompanyAgents;
        context.update();
    })
}

export class OnlineMarketingCompaniesState extends FrontendState {
    component = <OnlineMarketingCompaniesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.onlineMarketing.companies !== null)
            return frontendState.DONE;
    }
}
