import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {optionallyPickCard} from "../../selector/pickers/Pickers";
import fetchAuthenticated from "../../../../util/fetchAuthenticated";
import {Info} from "../../../Match";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {consultant, getConsultantName} from "../../../data/MatchEnums";

function TakeoverConsultantPicker() {
    const context = useContext(StateContext);
    const info = useContext(Info);
    const state = context.state;


    const fetchConsultant =(body) => async () => {
        try {
            await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/consultants/use`, "POST", body)
                .then(async response => await response.json());
        } catch (error) {
            console.log(error.message)
        }
    };

    const selectableElements = [];
    for(let i = 0; i < context.playerConsultants.values.length; i++){
        const c = context.playerConsultants.values[i];
        if(c === consultant.DEALMAKER || c === consultant.MILITARY_CONTRACTOR)
            selectableElements.push(i)
    }

    return optionallyPickCard(context.playerConsultants.components,
        (selected) => {
            const index = selected[0];
            state.takeover.consultant = getConsultantName(context.playerConsultants.values[index]);

            const body = {consultant: state.takeover.consultant};
            fetchConsultant(body)();
            context.update();
        },
        () => {
            const body = {consultant: null};
            fetchConsultant(body)();
            state.takeover.consultant = -1;
            context.update();
        },
        selectableElements
    )
}

export class TakeoverConsultantState extends FrontendState {
    component = <TakeoverConsultantPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.consultant !== null)
            return frontendState.takeover.PICK_CONGLOMERATES;
    }
}
