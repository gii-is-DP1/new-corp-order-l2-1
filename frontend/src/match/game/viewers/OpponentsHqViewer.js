import {useContext} from "react";
import {StateContext} from "../Game";

export function OpponentsHqViewer() {
    const context = useContext(StateContext);
    return context.opponents.components;
}
