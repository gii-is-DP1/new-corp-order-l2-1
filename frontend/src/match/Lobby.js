import {MatchCode} from "./MatchCode";
import {useState} from "react";
import BaseModal from "../components/BaseModal";
import {Player} from "./Player";

export function Lobby({matchInfo, isAdmin, matchCode}) {
    return <>
        <MainMessage matchInfo={matchInfo}/>
        <PlayersContainer matchInfo={matchInfo} isAdmin={isAdmin}/>
        <MatchCode matchCode={matchCode}/>
    </>
}

function PlayersContainer({matchInfo, isAdmin}) {
    const [show, setShow] = useState(false);
    const [playerName, setPlayerName] = useState("");
    const style = {
        display: "flex",
        justifyContent: "space-evenly",
        flex: 3,
        width: "100%",
    }
    return <div style={style}>
        <BaseModal state={[show, setShow]} title="Kicking player"
                   body={"Are you sure you want to kick " + playerName + "?"}/>
        {[...Array(matchInfo.maxPlayers)].map((x, i) => {
            const player = matchInfo.players[i];
            return <Player key={i} player={player} isAdmin={isAdmin} onKick={() => {
                setPlayerName(player.username);
                setShow(true);
            }}/>
        })}
    </div>
}

function MainMessage(props) {
    const style = {
        flex: 2.5,
        display: "flex",
        justifyContent: "center",
        flexDirection: "column"
    }
    const text = props.matchInfo.players.length === props.matchInfo.maxPlayers
        ? "Starting..."
        : "Waiting for players...";

    return <h2 style={style}> {text}</h2>;
}
