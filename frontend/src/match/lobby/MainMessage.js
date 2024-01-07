import css from "./lobby.module.css";
import {useContext} from "react";
import {Info} from "../Match";

export function MainMessage() {
    const info = useContext(Info);
    const isLobbyFull = info.players.length === info.maxPlayers;
    const text = isLobbyFull
        ? "Starting..."
        : "Waiting for players...";

    return <h2 className={css.mainMessage}> {text}</h2>;
}
