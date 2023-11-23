import {useParams} from "react-router-dom";
import {useState} from "react";
import BaseButton, {black, buttonContext, buttonContexts, buttonStyle, buttonStyles, white} from "../components/Button";
import GoBackButton from "../components/GoBackButton";
import BaseModal from "../components/BaseModal";
import ProfilePicture from "../components/ProfilePicture";
import Chat from "../chat/chat";

const matchInfo = {
    maxPlayers: 4,
    players: [
        {username: "Gioacchino", propic: "https://th.bing.com/th/id/OIG.brPoGXf3gGgrVkV9ixtc?w=173&h=173&c=6&r=0&o=5&dpr=1.3&pid=ImgGn"},
        {username: "beluga", propic: "https://th.bing.com/th/id/OIG.oi__xz_IswoFyfQ60TwA?w=173&h=173&c=6&r=0&o=5&dpr=1.3&pid=ImgGn"}
    ]
};
const isAdmin = true;

function Match(/*{isAdmin, matchInfo}*/) {
    const {id} = useParams();
    const [count, setCount] = useState(0);

    const style = {
        display: "flex",
        width: "100%",
        height: "100vh",
    }

    return (
        <div style={style}>
            <Main matchInfo={matchInfo} isAdmin={isAdmin} id={id} className="mainPart"/>
            <RightBar/>
        </div>)
}

function Main({matchInfo, isAdmin, id}) {
    const style = {
        backgroundColor: "#2c2c2c",
        flex: 0.7,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
    }

    return <div style={style}>
        <MainMessage matchInfo={matchInfo}/>
        <PlayersContainer matchInfo={matchInfo} isAdmin={isAdmin}/>
        <MatchCode code={id}/>
    </div>
}

function PlayersContainer({matchInfo, isAdmin}) {
    const [show, setShow] = useState(false);
    const [playerName, setPlayerName] = useState("");
    return <div className="players-container">
        <BaseModal state = {[show, setShow]} title="Kicking player" body={"Are you sure you want to kick "+playerName+"?"}/>
        {[...Array(matchInfo.maxPlayers)].map((x, i) =>
            <>
                <Player player={matchInfo.players[i] ?? {}} isAdmin={isAdmin} nth={i} onKick={() =>{setPlayerName(matchInfo.players[i].username); setShow(true);}}/>
            </>
        )}
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

function MainMessage(props) {
    const style = {
        flex: 2.5,
        display: "flex",
        justifyContent: "center",
        flexDirection: "column",
    }
    return <h2 style = {style}> {props.matchInfo.players.length === props.matchInfo.maxPlayers
        ? "Waiting for host..."
        : "Waiting for players..."
    } </h2>
}

function MatchCode(props) {
    const [isMatchCodeVisible, setIsMatchCodeVisible] = useState(true);

    return (<i className="matchCode">
        <span> {isMatchCodeVisible ? props.code : "hidden code"} </span>
        <span onClick={() => setIsMatchCodeVisible(!isMatchCodeVisible)}>
            {isMatchCodeVisible
                ? <svg
                    xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" className="bi bi-eye"
                    viewBox="0 0 16 16">
                    <path
                        d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                    <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                </svg>
                : <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor"
                       className="bi bi-eye-slash" viewBox="0 0 16 16">
                    <path
                        d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7.028 7.028 0 0 0-2.79.588l.77.771A5.944 5.944 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.134 13.134 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755-.165.165-.337.328-.517.486l.708.709z"/>
                    <path
                        d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829l.822.822zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z"/>
                    <path
                        d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z"/>
                </svg>
            }
        </span>
    </i>)
}

function Player({player, nth, isAdmin, onKick = () => {}}) {
    const style = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
    }
    return (
        <div style = {style}>
            <ProfilePicture url={player.propic} isTransparent = {player.username == null}/>
            {
                player.username == null
                    ? <></>//<p>waiting for player #{nth + 1} </p>
                    : <p>{player.username}</p>
            }
            {isAdmin && player.username != null
                ?
                <BaseButton buttonStyle={buttonStyles.primary} buttonContext={buttonContexts.light} onClick={() => onKick()}>Kick</BaseButton>
                : <></>
            }
        </div>
    );
}



export default Match;


