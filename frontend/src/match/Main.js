import css from "./match.module.css";
import {Lobby} from "./lobby/Lobby";
import {Game} from "./game/Game";
import React from "react";
import {Info} from "./Match";

export function Main() {
    return <div className={css.main}>
        <Info.Consumer>
            {info => info.inLobby
                ? <Lobby/>
                : <Game/>
            }
        </Info.Consumer>
    </div>
}
