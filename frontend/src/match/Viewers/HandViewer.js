import {ViewerContainer} from "../Viewer";
import React from "react";

export function HandViewer({items}) {
    return <ViewerContainer title="Your Hand" containerStyle={{display: "flex", flexWrap: "wrap"}}
                            buttonContent={<p>View Hand</p>}
                            items={items}/>
}
