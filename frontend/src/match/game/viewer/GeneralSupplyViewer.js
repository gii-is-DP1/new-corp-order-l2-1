import {ViewerContainer} from "../../Viewer";
import Deck from "../../Deck";
import React from "react";

export function GeneralSupplyViewer({items}) {
    return <ViewerContainer title="General Supply" containerStyle={{display: "flex", flexWrap: "wrap"}}
                            buttonContent={<p>View General Supply</p>}
                            items={items}/>
}
