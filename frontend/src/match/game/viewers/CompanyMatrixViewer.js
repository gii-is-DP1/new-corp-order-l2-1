import React, {useContext} from "react";
import {StateContext} from "../Game";
import {ViewerContainer} from "./Viewer";

export function CompanyMatrixViewer() {
    const context = useContext(StateContext);
    return <Companies items={context.companyTiles.components}/>
}

function Companies({items}) {
    return <ViewerContainer title="Company Matrix" itemsStyle={{maxHeight: "25vh", maxWidth: "25%", flexShrink: 1}}
                            containerStyle={{
                                overflow: "auto",
                                display: "flex",
                                flexWrap: "wrap",
                                flexShrink: 1,
                                maxWidth: "80vh"
                            }}
                            buttonContent={<p>View Company Matrix</p>}
                            items={items}/>
}
