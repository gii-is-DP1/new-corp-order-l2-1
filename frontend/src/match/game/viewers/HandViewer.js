import {ViewerContainer} from "./Viewer";
import React, {useContext} from "react";
import {StateContext} from "../Game";

export function HandViewer({items}) {
    const context = useContext(StateContext);
    return <Hand items={context.hand.components}/>
}

function Hand({items}) {
    return (
        <div style={{
            bottom: 0, position: "absolute",
            right: 0,
            left: "50%",
            transform: "translate(-50%, 0)",
            maxWidth:"130px"
        }}>
            <ViewerContainer title="Your Hand" containerStyle={{}}
                             buttonContent={<p>View Hand</p>}
                             items={items}/>
        </div>);
}
