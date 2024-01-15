import React, {useContext} from "react";
import {Info} from "../Match";

export function Winner() {
    const info = useContext(Info);
    if(info.isWinner)
        return <><p>YOU WON</p></>
    else
        return <><p>YOU LOST</p></>
}
