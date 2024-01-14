import {FrontendState} from "../FrontendState";
import {consultant, getConglomerateName} from "../../../data/MatchEnums";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";

function MediaAdvisorConglomeratePicker() {
        const context = useContext(StateContext);
        const info = useContext(Info);

        return pickOneCard(context.hand.components, (selected) => {
            context.state.infiltrate.mediaAdvisor.conglomerate = getConglomerateName(context.hand.values[selected[0]]);
            context.update();

            const infiltrateBody = {
                infiltrate: {
                    numberOfShares: context.state.infiltrate.conglomerateQuantity,
                    conglomerateType: context.state.infiltrate.conglomerate,
                    tile: {
                        x: context.state.infiltrate.companyTile % 4,
                        y: Math.floor(context.state.infiltrate.companyTile / 4),
                    },
                    extraConglomerate: context.state.infiltrate.mediaAdvisor.conglomerate,
                    type: "MediaAdvisorInfiltrate"
                }
            };

            const infiltrateFetch = async () => {
                try {
                    await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/infiltrate`, "POST", infiltrateBody)
                        .then(async response => await response.json());
                } catch (error) {
                    console.log(error.message)
                }
            };
            infiltrateFetch();
        })
    }

export class MediaAdvisorConglomerateState extends FrontendState {
    component = <MediaAdvisorConglomeratePicker/>

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.mediaAdvisor.conglomerate !== null)
            return frontendState.DONE;
    }
}
