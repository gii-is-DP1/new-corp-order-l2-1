import {FrontendState} from "../FrontendState";
import {consultant} from "../../../data/MatchEnums";
import React, {useContext} from "react";
import {pickCompany} from "../../selector/pickers/Pickers";
import {StateContext} from "../../Game";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";

export class InfiltrateCompanyState extends FrontendState {
    component = <InfiltrateCompanyPicker/>;

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.companyTile !== null) {
            if (gameState.infiltrate.consultant === "NONE")
                return frontendState.DONE;
            if (gameState.infiltrate.consultant === consultant.MEDIA_ADVISOR)
                return frontendState.infiltrate.MEDIA_ADVISOR_PICK_CONGLOMERATE;
            if (gameState.infiltrate.consultant === consultant.CORPORATE_LAWYER)
                return frontendState.infiltrate.CORPORATE_LAWYER_PICK_CONGLOMERATES;
        }
    }
}

export function InfiltrateCompanyPicker() { //TODO: implement picker
    const context = useContext(StateContext);
    const info = useContext(Info);
    return pickCompany(context.companyTiles.components, selected => {
        context.state.infiltrate.companyTile = selected[0];
        context.update();

        if(context.state.infiltrate.consultant === "NONE") {
            const infiltrateBody = {
                infiltrate: {
                    numberOfShares: context.state.infiltrate.conglomerateQuantity,
                    conglomerateType: context.state.infiltrate.conglomerate,
                    tile: {
                        x: context.state.infiltrate.companyTile % 4,
                        y: Math.floor(context.state.infiltrate.companyTile / 4),
                    },
                    type: "BasicInfiltrate"
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
        }
    }, 1);
}
