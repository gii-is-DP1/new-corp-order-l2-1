import React, {useContext} from "react";
import {StateContext} from "../../Game";
import Deck from "../../../components/Deck";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {Info} from "../../../Match";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";

export function DrawConglomerate(isFirst) {
    const context = useContext(StateContext);
    const info = useContext(Info);
    const openDisplayAndDeck = [...context.openDisplay.components, <Deck/>];

    return pickOneCard(openDisplayAndDeck, selected => {
        const selectedConglomerate = openDisplayAndDeck[selected[0]];

        if (isFirst)
            context.state.plot.firstConglomerate = selectedConglomerate
        else
            context.state.plot.secondConglomerate = selectedConglomerate
        const plotRequest = {source: selected[0] === openDisplayAndDeck.length - 1 ? "DECK" : "OPEN_DISPLAY", conglomerate: selectedConglomerate.type};
        const fetchPropic = async () => {
            try {
                await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/plot`, "POST", plotRequest)
                    .then(async response => await response.json());
            } catch (error) {
                console.log(error.message)
            }
        };
        fetchPropic();
        context.update();
    })
}
