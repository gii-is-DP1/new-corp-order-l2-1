import {ViewerContainer} from "./Viewer";
import Deck from "../../components/Deck";
import React, {useContext} from "react";
import {StateContext} from "../Game";

export function GeneralSupplyViewer() {
    const context = useContext(StateContext);
    const items = [
        ...context.openDisplay.components,
        <Deck/>,
        ...context.generalSupplyConsultants.components]
    ;
    return <GeneralSupply items={items}/>
}

function GeneralSupply({items}) {
    return <ViewerContainer title="General Supply" containerStyle={{display: "flex", flexWrap: "wrap"}}
                            buttonContent={<p>General Supply</p>}
                            items={items}/>
}
