import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {optionallyPickCard} from "../../selector/pickers/Pickers";
import fetchAuthenticated from "../../../../util/fetchAuthenticated";
import {Info} from "../../../Match";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";

function TakeoverConsultantPicker() {
    const context = useContext(StateContext);
    const info = useContext(Info);
    const state = context.state;
    const fetchAction = async () => {
        try {
            await fetchAuthenticated(`/api/v1/matches/${info.code}/turn?action=TAKE_OVER`, "POST")
                .then(async response => await response.json());
        } catch (error) {
            console.log(error.message)
        }
    };

    return optionallyPickCard(context.playerConsultants.components,
        (selected) => {
            fetchAction();
            const index = selected[0];
            state.takeover.consultant = context.playerConsultants.values[index];
            context.update();
        },
        () => {
            fetchAction();
            const body = {consultant: null};
            const fetchConsultant = async () => {
                try {
                    await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/consultants/use`, "POST", body)
                        .then(async response => await response.json());
                } catch (error) {
                    console.log(error.message)
                }
            };
            fetchConsultant();
            state.takeover.consultant = -1;
            context.update();
        })
}

export class TakeoverConsultantState extends FrontendState {
    component = <TakeoverConsultantPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.consultant !== null)
            return frontendState.takeover.PICK_CONGLOMERATES;
    }
}
