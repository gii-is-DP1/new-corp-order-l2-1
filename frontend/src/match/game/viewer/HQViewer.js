import {ViewerContainer} from "../../Viewer";
import React from "react";

export function HQViewer({hqItems}) {
    return <ViewerContainer title="Your HQ" containerStyle={{display: "flex", flexWrap: "wrap"}}
                            buttonContent={<p>View Hq</p>}
                            items={hqItems}/>
}
