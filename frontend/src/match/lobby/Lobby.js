import {MatchCode} from "./MatchCode";
import {MainMessage} from "./MainMessage";
import {LobbyPlayers} from "./LobbyPlayers";
import {Info} from "../Match";

export function Lobby() {
    return <>
        <MainMessage/>
        <LobbyPlayers/>
        <ContextCode/>
    </>
}

function ContextCode() {
    return (
        <Info.Consumer>
            {info => <MatchCode code={info.code}/>}
        </Info.Consumer>
    );
}
