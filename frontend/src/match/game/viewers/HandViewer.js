import {ViewerContainer} from "./Viewer";
import React, {useContext} from "react";
import {StateContext} from "../Game";

export function HandViewer() {
    const context = useContext(StateContext);
    return <Hand items={context.hand.components}/>
}

function Hand({items}) {
    return (

            <ViewerContainer title="Your Hand" containerStyle={{display: "flex", flexWrap: "wrap"}}
                             buttonContent={<p>Hand</p>}
                             items={items}/>
        );
}
