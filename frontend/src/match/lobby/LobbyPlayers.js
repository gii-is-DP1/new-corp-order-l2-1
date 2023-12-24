import {useState} from "react";
import BaseModal from "../../components/BaseModal";
import {Player} from "../Player";
import "./lobby.css";

export function LobbyPlayers({matchInfo}) {
    const [show, setShow] = useState(false);
    const [playerName, setPlayerName] = useState("");

    const playerInfoModal=  <BaseModal state={[show, setShow]}
                                       title="Kicking player"
                                       body={"Are you sure you want to kick " + playerName + "?"}
    />;

    return <div className={"playersContainer"}>
        {playerInfoModal}
        {[...Array(matchInfo.maxPlayers)].map((x, i) => {
            const playerData = matchInfo.players[i];
            const onKick = () => {
                setPlayerName(playerData.username);
                setShow(true);
            }
            return <Player key={i} data={playerData} isAdmin={matchInfo.isAdmin} onKick={onKick}/>
        })}
    </div>
}
