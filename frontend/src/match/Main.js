import css from "./match.module.css";
import {Lobby} from "./lobby/Lobby";
import {Game} from "./game/Game";
import React from "react";
import {Info} from "./Match";
import {Winner} from "./game/Winner";

export function Main() {
    return <div className={css.main}>
        <Info.Consumer>
            {info => info.inLobby
                ? <Lobby key={info.code}/>
                : info.isWinner || info.isWinner === false ? <Winner key={info.code}/> : <Game  key={info.code}/>
            }
        </Info.Consumer>
    </div>
}
