import {useParams} from "react-router-dom";
import {useState} from "react";
import GoBackButton from "../components/GoBackButton";
import Chat from "../chat/chat";
import {black} from "../util/Colors";
import {Lobby} from "./Lobby";
import {Game} from "./Game";

/* Possible actions
1) Card Select View
    1 Select n conglomerates of the same color from hand (Infiltrate)
    2 Select n conglomerates of any color from hand (To discard)
    3 Select n conglomerates of the same color from hq (Takeover)
    4 Select card from deck and/or Open Display (Plot)
    16 Select two cards from deck (Takeover -> Dealmaker)
    5 Select (a not used in this turn) consultant from General Supply (Infiltrate)
2) Simple View
    6 View secret objectives
    7 View General Supply
3) Company tile Select View
    8 Select a company tile with agents of a specific color (Infiltrate, Takeover)
    9 Select a company tile orthogonally adjacent at a specific one (Takeover)
4) Company Abilities
    10 Select if you want to use a specific company ability (Takeover)
    11 Select one or two company tiles with agents of a specific color (Broadcast Network)
    12 Select one HQ from everyone's HQ conglomerates (Guerrilla Marketing, Ambient Advertising, Social Media)
    13 Select n (one or two) conglomerates from a specific HQ (Guerrilla Marketing, Ambient Advertising, Social Media)
    14 Select one conglomerate from your HQ and one from everyone else's HQ Conglomerate (Print Media)
    15 Select two different company tiles
 */

const matchInfo = {
    maxPlayers: 4,
    players: [
        {
            username: "Gioacchino",
            propic: "https://th.bing.com/th/id/OIG.brPoGXf3gGgrVkV9ixtc?w=173&h=173&c=6&r=0&o=5&dpr=1.3&pid=ImgGn"
        },
        {
            username: "beluga",
            propic: "https://th.bing.com/th/id/OIG.oi__xz_IswoFyfQ60TwA?w=173&h=173&c=6&r=0&o=5&dpr=1.3&pid=ImgGn"
        }
    ]
};
const isAdmin = true;
const inLobby = false;
let matchCode;

function Match(/*{isAdmin, matchInfo}*/) {
    const {id} = useParams();
    matchCode = id;
    const [count, setCount] = useState(0);

    const style = {
        display: "flex",
        width: "100%",
        height: "100vh",
        overflow:"hidden",
    }

    return (
        <div style={style}>
            <Main matchInfo={matchInfo} isAdmin={isAdmin} className="mainPart"/>
            <RightBar/>
        </div>)
}

function Main() {
    const style = {
        backgroundColor: black,
        flex: 0.7,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
    }

    return <div style={style}>
        {inLobby
            ? <Lobby matchInfo={matchInfo} isAdmin={isAdmin} matchCode={matchCode}/>
            : <Game/>
        }
    </div>
}

function RightBar() {
    const style = {
        backgroundColor: "#404040",
        flex: 0.3,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
    }
    return <div style={style}>
        <GoBackButton/>
        <Chat/>
    </div>
}

export default Match;
