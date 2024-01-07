import React, {useContext} from "react";
import {StateContext} from "../../Game";
import Deck from "../../../components/Deck";
import {pickOneCard} from "../../selector/pickers/Pickers";

export function DrawConglomerate(isFirst) {
    const context = useContext(StateContext);
    const openDisplayAndDeck = [...context.openDisplay.components, <Deck/>];

    return pickOneCard(openDisplayAndDeck, selected => {
        const selectedConglomerate = openDisplayAndDeck[selected[0]];

        if (isFirst)
            context.state.plot.firstConglomerate = selectedConglomerate
        else
            context.state.plot.secondConglomerate = selectedConglomerate
        context.update();
    })
}
