import {MatchCode} from "./MatchCode";
import {MainMessage} from "./MainMessage";
import {LobbyPlayers} from "./LobbyPlayers";

export function Lobby({matchInfo}) {
    return <>
        <MainMessage matchInfo={matchInfo}/>
        <LobbyPlayers matchInfo={matchInfo}/>
        <MatchCode matchCode={matchInfo.code}/>
    </>
}
