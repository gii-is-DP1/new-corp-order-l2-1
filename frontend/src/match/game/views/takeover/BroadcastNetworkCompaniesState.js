import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickCompany, pickTwoOrThreeCompanies} from "../../selector/pickers/Pickers";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";

function BroadcastNetworkCompaniesPicker(){
    const context= useContext(StateContext);
    const info = useContext(Info);
    const state = context.state;
    return pickTwoOrThreeCompanies(context.companyTiles.components, (selected) => {
        state.takeover.ability.broadcastNetwork.companies = selected.map(index => state.game.companies[index]);
        let body;
        if(selected.length === 2) {
            body = {companyAbility: {type: "BroadcastNetworkAbility",
                    firstTarget: {x: selected[1] % 4, y: Math.floor(selected[1] / 4)},
                    secondTarget: null,
                    sourceCompany: {x: selected[0] % 4, y: Math.floor(selected[0] / 4)}}};
        }
        if(selected.length === 3) {
            body = {companyAbility: {type: "BroadcastNetworkAbility",
                    firstTarget: {x: selected[1] % 4, y: Math.floor(selected[1] / 4)},
                    secondTarget: {x: selected[2] % 4, y: Math.floor(selected[2] / 4)},
                    sourceCompany: {x: selected[0] % 4, y: Math.floor(selected[0] / 4)}}};
        }
        const fetchCompany = async () => {
            try {
                await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/company-abilities`, "POST", body)
                    .then(async response => await response.json());
            } catch (error) {
                console.log(error.message)
            }
        };
        fetchCompany();
        context.update();
    },
        context.companyTiles.values
    )
}

export class BroadcastNetworkCompaniesState extends FrontendState {
    component = <BroadcastNetworkCompaniesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.broadcastNetwork.companies !== null)
            return frontendState.DONE;
    }
}
