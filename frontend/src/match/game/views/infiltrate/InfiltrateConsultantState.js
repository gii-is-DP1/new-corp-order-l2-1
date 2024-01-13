import {useContext} from "react";
import {StateContext} from "../../Game";
import {optionallyPickCard, pickOneCard} from "../../selector/pickers/Pickers";
import {FrontendState} from "../FrontendState";
import {consultant} from "../../../data/MatchEnums";
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
            const body = {
                infiltrate: {
                    numberOfShares: context.state.infiltrate.conglomerateQuantity,
                    conglomerateType: context.state.infiltrate.conglomerate,
                    tile: {
                        x: 0, //context.state.infiltrate.companyTile / 4,
                        y: 0, //context.state.infiltrate.companyTile % 4,
                    },
                    type: "BasicInfiltrate"
                }
            };
            console.log(context.state.infiltrate);
            console.log(body);

            const fetch = async () => {
                try {
                    await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/infiltrate`, "POST", body)
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
        if (gameState.infiltrate.consultant !== null) {
            if (gameState.infiltrate.consultant === "NONE")
                return frontendState.DONE;
            if (gameState.infiltrate.consultant === consultant.MEDIA_ADVISOR)
                return frontendState.infiltrate.MEDIA_ADVISOR_PICK_CONGLOMERATE;
            if (gameState.infiltrate.consultant === consultant.CORPORATE_LAWYER)
                return frontendState.infiltrate.CORPORATE_LAWYER_PICK_CONGLOMERATES;
        }
    }
}

