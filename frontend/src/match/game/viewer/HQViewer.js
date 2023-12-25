import {ViewerContainer} from "./Viewer";
import React, {useContext} from "react";
import {StateContext} from "../Game";

export function HQViewer() {
    const context = useContext(StateContext);
    const hqItems = [
        ...context.hqConglomerates.components,
        ...context.hqConsultants.components,
        ...context.secretObjectives.components
    ];

    return <Hq hqItems={hqItems}/>
}

function Hq({hqItems}){
    return <ViewerContainer title="Your HQ" containerStyle={{display: "flex", flexWrap: "wrap"}}
                            buttonContent={<p>View Hq</p>}
                            items={hqItems}/>
}
