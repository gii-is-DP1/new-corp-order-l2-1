import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import fetchAuthenticated from "../../../../util/fetchAuthenticated";
import {Info} from "../../../Match";
import {getConsultantName} from "../../../data/MatchEnums";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";

function InfiltrateNewConsultantDrawer() {
    const context = useContext(StateContext);
    const state = context.state;
    const info = useContext(Info);

    return pickOneCard(context.generalSupplyConsultants.components, (selected) => {
        const index = selected[0];
        state.infiltrate.consultant = context.generalSupplyConsultants.values[index];
        context.update();

        const  body = {
            consultant: getConsultantName(context.state.infiltrate.takenConsultant)
        }
        const fetch = async () => {
            try {
                await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/consultants/take`, "POST", body)
                    .then(async response => await response.json());
            } catch (error) {
                console.log(error.message)
            }
        };
        fetch();
    })
}

export class InfiltrateNewConsultantState extends FrontendState {
    component = <InfiltrateNewConsultantDrawer/>

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.takenConsultant !== null)
            return frontendState.DONE;
    }
}
