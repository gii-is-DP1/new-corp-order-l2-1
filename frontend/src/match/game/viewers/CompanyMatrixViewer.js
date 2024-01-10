import React, {useContext} from "react";
import {StateContext} from "../Game";
import {Viewable, Viewer, ViewerContainer} from "./Viewer";

export function CompanyMatrixViewer() {
    const context = useContext(StateContext);
    return <Companies items={context.companyTiles.components}/>
}

function Companies({items}) {
    return (
        <div style={{
            overflow: "auto",
            display: "flex",
            flexWrap: "wrap",
            flexShrink: 1,
            maxWidth: "80vh",
        }}>
            {items.map(item =>
                <Viewable item={item} style={{maxHeight: "25vh", maxWidth: "25%", flexShrink: 1}}/>
            )}
        </div>
    );
}
