import {FrontendState} from "../FrontendState";
import {PlotFirstConglomeratePicker} from "../plot/PlotFirstConglomeratePicker";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickCompany, pickManyConglomeratesOfTheSameColor, pickOneCard} from "../../selector/pickers/Pickers";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";

function CorporateLawyerCompanyPicker() {
    const context = useContext(StateContext);
    const info = useContext(Info);

    return pickCompany(context.companyTiles.components, (selected) => {
        context.state.infiltrate.corporateLawyer.company = selected[0];
        context.update();

        const infiltrateBody = {
            infiltrate: {
                actions: [
                    {
                        numberOfShares: context.state.infiltrate.conglomerateQuantity,
                        conglomerateType: context.state.infiltrate.conglomerate,
                        tile: {
                            x: context.state.infiltrate.companyTile % 4,
                            y: Math.floor(context.state.infiltrate.companyTile / 4),
                        },
                        type: "BasicInfiltrate"
                    },
                    {
                        numberOfShares: context.state.infiltrate.corporateLawyer.conglomerates.quantity,
                        conglomerateType: context.state.infiltrate.corporateLawyer.conglomerates.type,
                        tile: {
                            x: context.state.infiltrate.corporateLawyer.company % 4,
                            y: Math.floor(context.state.infiltrate.corporateLawyer.company / 4),
                        },
                        type: "BasicInfiltrate"
                    }
                ],
                type: "CorporateLawyerInfiltrate"
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
    }, 1)
}

export class CorporateLawyerCompanyState extends FrontendState {
    component = <CorporateLawyerCompanyPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.corporateLawyer.company !== null)
            return frontendState.DONE;
    }
}
