import React, {useContext} from "react";
import {StateContext} from "../../Game";
import Deck from "../../../components/Deck";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {Info} from "../../../Match";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import fetchAuthenticated from "../../../../util/fetchAuthenticated";
import {conglomerate} from "../../../data/MatchEnums";

export function DrawConglomerate(isFirst) {
    const context = useContext(StateContext);
    const info = useContext(Info);
    const openDisplayAndDeck = [...context.openDisplay.components, <Deck/>];


    return pickOneCard(openDisplayAndDeck, selected => {
        const index = selected[0];
        const selectedConglomerate = context.openDisplay.values[index];
        if (isFirst) {
            context.state.plot.firstConglomerate = selectedConglomerate;
            const fetchAction = async () => {
                try {
                    await fetchAuthenticated(`/api/v1/matches/${info.code}/turn?action=PLOT`, "POST")
                        .then(async response => await response.json());
                } catch (error) {
                    console.log(error.message)
                }
            };
            fetchAction();
        }
        else{
            context.state.plot.secondConglomerate = selectedConglomerate;
        }

        let plotRequest;
        let conglomerateType;

        if(selected[0] === openDisplayAndDeck.length - 1)
            plotRequest = {source: "DECK"}
        else {
            conglomerateType = Object.keys(conglomerate).find(key => conglomerate[key] === selectedConglomerate);
            plotRequest = {
                source: "OPEN_DISPLAY",
                conglomerate: conglomerateType
            }
        }
        const postPlot = async () => {
        try {
            await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/plot`, "POST", plotRequest)
                .then(async response => await response.json());
        } catch (error) {
            console.log(error.message)
        }
        };
        postPlot();
        if(conglomerateType !== null)
            context.state.game.generalSupply.openDisplay[conglomerateType]--;
        context.update();
    })
}
