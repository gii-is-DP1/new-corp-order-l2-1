import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickTwoCompanies} from "../../selector/pickers/Pickers";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";

function OnlineMarketingCompaniesPicker(){
    const context = useContext(StateContext);
    const info = useContext(Info);
    const state = context.state;
    return pickTwoCompanies(context.companyTiles.components, (selected) => {
        state.takeover.ability.onlineMarketing.companies = selected.map(index => state.game.companies[index]);
        const body = {companyAbility: {type: "OnlineMarketingAbility",
                firstCompany: {x: selected[0] % 4, y: Math.floor(selected[0] / 4)},
                secondCompany: {x: selected[1] % 4, y: Math.floor(selected[1] / 4)}}};
        const fetchCompany = async () => {
            try {
                await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/company-abilities`, "POST", body)
                    .then(async response => await response.json());
            } catch (error) {
                console.log(error.message)
            }
        };
        fetchCompany();
        /*const firstCompanyType = state.game.companies[selected[0]].type;
        state.game.companies[selected[0]].type = state.game.companies[selected[1]].type;
        state.game.companies[selected[1]].type = firstCompanyType;
        const firstCompanyAgents = state.game.companies[selected[0]].agents;
        state.game.companies[selected[0]].agents = state.game.companies[selected[1]].agents;
        state.game.companies[selected[1]].agents = firstCompanyAgents;*/
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
