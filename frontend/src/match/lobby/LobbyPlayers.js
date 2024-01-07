import {useContext, useState} from "react";
import BaseModal from "../../components/BaseModal";
import {Player} from "../components/Player";
import css from "./lobby.module.css";
import {Info} from "../Match";

export function LobbyPlayers() {
    const info = useContext(Info);
    const [showModal, setShowModal] = useState(false);
    const [selectedPlayerName, setSelectedPlayerName] = useState("");

    const playerInfoModal=  <BaseModal state={[showModal, setShowModal]}
                                       title="Kicking player"
                                       body={"Are you sure you want to kick " + selectedPlayerName + "?"}
    />;

    return <div className={css.playersContainer}>
        {playerInfoModal}
        {[...Array(info.maxPlayers)].map((x, i) => {
            const playerData = info.players[i] ?? {};
            const onKick = () => {
                setSelectedPlayerName(playerData.username);
                setShowModal(true);
            }
            return <Player key={i} data={playerData} isAdmin={info.isAdmin} onKick={onKick}/>
        })}
    </div>
}
