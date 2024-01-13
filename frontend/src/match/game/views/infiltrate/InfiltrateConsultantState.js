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

    return optionallyPickCard(context.playerConsultants.components,
        (selected) => {
            const index = selected[0];
            context.state.infiltrate.consultant = context.playerConsultants.values[index];
            context.update();
        },
        () => {
            const fetchAction = async () => {
                try {
                    await fetchAuthenticated(`/api/v1/matches/${info.code}/turn?action=INFILTRATE`, "POST");
                } catch (error) {
                    console.log(error.message)
                }
            };
            fetchAction();




            //console.log(context.state.infiltrate);
            //console.log(body);

           const  body = {
               consultant: getConsultantName(context.state.infiltrate.consultant)
           }
            const fetch = async () => {
                try {
                    await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/consultants/use`, "POST", body)
                        .then(async response => await response.json());
                } catch (error) {
                    console.log(error.message)
                }
            };
            fetch();




            context.state.infiltrate.consultant = "NONE";
            context.update();
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

