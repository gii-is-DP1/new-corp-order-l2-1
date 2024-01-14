import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {optionallyPickCard, pickOneCard} from "../../selector/pickers/Pickers";
import fetchAuthenticated from "../../../../util/fetchAuthenticated";
import {Info} from "../../../Match";
import {getConsultantName} from "../../../data/MatchEnums";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";

function InfiltrateNewConsultantDrawer() {
    const context = useContext(StateContext);
    const state = context.state;
    const info = useContext(Info);
    if(state.infiltrate.takenConsultant !== null)
    {
        console.log("THIS SHOULD NO HAPPEN")
        return;
    }


    return optionallyPickCard(context.generalSupplyConsultants.components,
        (selected) => {
            const index = selected[0];

            state.infiltrate.takenConsultant = context.generalSupplyConsultants.values[index];
            context.update();

            const body = {
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
        },
        () => {
            state.infiltrate.takenConsultant = "NONE";
            context.update();

            const body = {
                consultant: null
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
        }
    )
}

export class InfiltrateNewConsultantState extends FrontendState {
    component = <InfiltrateNewConsultantDrawer/>

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.takenConsultant !== null)
            return frontendState.DONE;
    }
}
