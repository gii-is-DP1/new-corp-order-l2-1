import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {
    pickOrthogonallyAdjacentCompanyTilesWithColors
} from "../../selector/pickers/Pickers";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";
import {getConglomerateName} from "../../../data/MatchEnums";

function TakeoverCompanyTilesPicker() {
    const context = useContext(StateContext);
    const info = useContext(Info);
    const state = context.state;
    return pickOrthogonallyAdjacentCompanyTilesWithColors(context.companyTiles.components, (selected) => {
        state.takeover.companyTiles = selected.map(index => state.game.companies[index]);
        const body = {
            agents: state.takeover.conglomerates.agents,
            sourceCompany: {x: selected[0] % 4, y: Math.floor(selected[0] / 4)},
            targetCompany: {x: selected[1] % 4, y: Math.floor(selected[1] / 4)}
        };
        const fetchAction = async () => {
            try {
                await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/take-over`, "POST", body)
                    .then(async response => await response.json());
            } catch (error) {
                console.log(error.message)
            }
        };
        fetchAction();
        context.update();
    }, getConglomerateName(state.takeover.conglomerates.type), state.takeover.conglomerates.agents, context.companyTiles.values)
}

export class TakeoverCompanyTilesState extends FrontendState {
    component = <TakeoverCompanyTilesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.companyTiles !== null)
            return frontendState.takeover.ACTIVATE_ABILITY;
    }
}
