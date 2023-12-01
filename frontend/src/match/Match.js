import {useParams} from "react-router-dom";
import {useState} from "react";
import GoBackButton from "../components/GoBackButton";
import Chat from "../chat/chat";
import {black} from "../util/Colors";
import {Lobby} from "./Lobby";
import {Game} from "./Game";

/* Possible actions
1) Card Select View
    DONE 1 Select n conglomerates of the same color from hand (Infiltrate)
    DONE 2 Select n conglomerates of any color from hand (To discard)
    DONE 3 Select n conglomerates of the same color from hq (Takeover)
    DONE 4 Select a card from deck or Open Display (Plot)
    DONE 16 Select two cards from deck (Takeover -> Dealmaker)
    DONE 5 Select (a not used in this turn) consultant from General Supply (Infiltrate)
2) Simple View
    6 View secret objectives
    7 View General Supply
3) Company tile Select View
    DONE 8 Select a company tile with agents of a specific color (Infiltrate, Takeover)
    DONE 9 Select a company tile orthogonally adjacent at a specific one (Takeover)
4) Company Abilities
    10 Select if you want to use a specific company ability (Takeover)
    DONE 11 Select one or two company tiles with agents of a specific color (Broadcast Network)
    DONE 12 Select one HQ from everyone's HQ conglomerates (Guerrilla Marketing, Ambient Advertising, Social Media)
    DONE 13 Select n (one or two) conglomerates from a specific HQ (Guerrilla Marketing, Ambient Advertising, Social Media)
    DONE 14 Select one conglomerate from your HQ and one from everyone else's HQ Conglomerate (Print Media)
    DONE 15 Select two different company tiles

    In general:
    1) Select n conglomerates of a selection of (any / the same) color from card set (hand / hq)
    2) Select a card from deck and/or Open Display
    3) Select (a not used in this turn) consultant from Consultants of general supply
    4) Select a company tile from a selection
    5) Select one or two company tiles from a selection
    6) Select yes or no with a company ability image
    7) Select HQ viewing conglomerates from a HQ selection
    8) Select (one / two) conglomerates from a specific HQ
    9) Select Conglomerate from everyone's HQ
    10) View secret objectives
        - Button that shows you Secret Objectives
    11) View General Supply (!1, !3)
        - Button that shows open display
        - Button that shows Consultants
    12) View everyone's HQ (!9)
        - Button to select HQ
    13) View everyone's profile
    14) Connect to Backend
    Match frontend: match state
    Sub-Branch 1)
        -Select n cards from card set (1,2,3)
        -(6)
        -Add button that hides the chat
        -Backend
    Sub-Branch 2)
        -Select company tiles (4, 5)
        -Backend
    Sub-Branch 3)
        - 7, 8, 9, 12
        -Backend
    Sub-Branch 4) 10, 11, 13
        -Backend
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
        flex: 10,
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
