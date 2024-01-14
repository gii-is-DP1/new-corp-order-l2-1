import {useContext} from "react";
import {StateContext} from "../../Game";
import {optionallyPickCard, pickOneCard} from "../../selector/pickers/Pickers";
import {FrontendState} from "../FrontendState";
import {consultant, getConsultantName} from "../../../data/MatchEnums";
import fetchAuthenticated from "../../../../util/fetchAuthenticated";
import tokenService from "../../../../services/token.service";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";

function InfiltrateConsultantPicker() { //TODO: add possibility of not choosing consultant
    const context = useContext(StateContext);
    const info = useContext(Info);


    const fetchConsultant = (body) => async () => {
        try {
            await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/consultants/use`, "POST", body)
                .then(async response => await response.json());
        } catch (error) {
            console.log(error.message)
        }
    };
    const makeRequest = (context) => {

        const  body = {
            consultant: getConsultantName(context.state.infiltrate.consultant)
        }
        fetchConsultant(body)();
    }

    return optionallyPickCard(context.playerConsultants.components,
        (selected) => {
            context.state.infiltrate.consultant = context.playerConsultants.values[selected[0]];
            context.update();
            makeRequest(context);
        },
        () => {
            context.state.infiltrate.consultant = "NONE";
            context.update();
            makeRequest(context);
        }
    )
}

export class InfiltrateConsultantState extends FrontendState {
    component = <InfiltrateConsultantPicker/>;

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.consultant !== null)
            return frontendState.infiltrate.PICK_CONGLOMERATES;
    }
}

