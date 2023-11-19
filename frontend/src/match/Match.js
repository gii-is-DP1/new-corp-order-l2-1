import Hand from "./Hand"
import {Link, useParams} from "react-router-dom";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

function Match() {
    const {id} = useParams();
    const isAdmin = true;
    const [count, setCount] = useState(0);
    console.log(id);
    const matchInfo = {
        maxPlayers: 4,
        players: [
            {username: "Gioacchino", propic: "https://fakeimg.pl/250/ff0000/"},
            {username: "beluga", propic: null}
        ]
    };

    return (
    <div className="match">
        <div className="mainPart">

            <MainMessage matchInfo={matchInfo}/>

            <div className="players-container">
                {[...Array(matchInfo.maxPlayers)].map((x, i) =>
                    <Player player={matchInfo.players[i] ?? {}} isAdmin={isAdmin} nth={i}/>
                )}
            </div>

            <MatchCode code={id}/>
        </div>

        <div className="rightBar">
            <QuitButton/>
            <div className="chat"/>
        </div>

    </div>)
}

function MainMessage(props) {
    return <div className="mainMessage">
        <p> {props.matchInfo.players.length === props.matchInfo.maxPlayers
            ? "Waiting for host..."
            : "Waiting for players..."
        } </p>
    </div>
}

function QuitButton() {
    return <Link className="quitButton" to="/">
        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" className="bi bi-box-arrow-left" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"/>
            <path fill-rule="evenodd" d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
        </svg>
    </Link>
}

function MatchCode(props) {
    const [isMatchCodeVisible, setIsMatchCodeVisible] = useState(true);

    return (<i className="matchCode">
        <span> {isMatchCodeVisible ? props.code : "hidden code"} </span>
        <span onClick = {() => setIsMatchCodeVisible(!isMatchCodeVisible)}>
            {isMatchCodeVisible
                ? <svg
                    xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" className="bi bi-eye" viewBox="0 0 16 16">
                    <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                    <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                </svg>
                : <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" className="bi bi-eye-slash" viewBox="0 0 16 16">
                    <path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7.028 7.028 0 0 0-2.79.588l.77.771A5.944 5.944 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.134 13.134 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755-.165.165-.337.328-.517.486l.708.709z"/>
                    <path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829l.822.822zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z"/>
                    <path d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z"/>
                </svg>
            }
        </span>
    </i>)
}

function Player(props) {
    return (
        <div className="userProfileDiv">

            <div className="propic">
                {
                    props.player.propic == null
                        ? <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                               className="bi bi-person" viewBox="0 0 16 16">
                            <path
                                d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0Zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4Zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10Z"/>
                        </svg>
                        : <img alt="profile image" src={props.player.propic}/>
                }
            </div>
            {
                props.player.username == null
                    ? <p>waiting for player #{props.nth + 1} </p>
                    : <p>{props.player.username}</p>
            }
            {props.isAdmin
                ? <button className="btn btn-danger"> Kick</button>
                : <></>
            }
        </div>
    );
}


export default Match;


