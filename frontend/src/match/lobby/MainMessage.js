import "./lobby.css";

export function MainMessage(props) {
    const text = props.matchInfo.players.length === props.matchInfo.maxPlayers
        ? "Starting..."
        : "Waiting for players...";

    return <h2 className="mainMessage"> {text}</h2>;
}
