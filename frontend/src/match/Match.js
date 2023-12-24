import GoBackButton from "../components/GoBackButton";
import Chat from "../chat/chat";
import {Lobby} from "./lobby/Lobby";
import {Game} from "./game/Game";
import {matchInfo} from "./MockupData";
import css from "./match.css";

export default function Match() {
    return (
        <div className={"match"}>
            <Main matchInfo={matchInfo}/>
            <RightBar/>
        </div>)
}

function Main() {
    return <div className={"main"}>
        {matchInfo.inLobby
            ? <Lobby matchInfo={matchInfo}/>
            : <Game/>
        }
    </div>
}

function RightBar() {
    return <div className={"rightBar"}>
        <GoBackButton/>
        <Chat/>
    </div>
}
