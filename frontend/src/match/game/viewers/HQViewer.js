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

function Hq({hqItems}) {
    return (<div style={{
        bottom: 0, position: "absolute",
        right: 0,
        left: "40%",
        transform: "translate(-50%, 0)",
        maxWidth: "130px"
    }}>

        <ViewerContainer title="Your HQ" containerStyle={{}}
                         buttonContent={<p>View Hq</p>}
                         items={hqItems}/>
    </div>);
}
