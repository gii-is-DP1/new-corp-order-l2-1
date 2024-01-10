import {MatchCode} from "./MatchCode";
import {MainMessage} from "./MainMessage";
import {LobbyPlayers} from "./LobbyPlayers";
import {Info} from "../Match";
import {useContext, useState} from "react";
import BaseModal from "../../components/BaseModal";
import fetchAuthenticated from "../../util/fetchAuthenticated";
import Button, {ButtonType} from "../../components/Button";
import {useNavigate} from "react-router-dom";


export function Lobby() {
    const info = useContext(Info);
    if(info.players.length === info.maxPlayers)
        startMatchRequest(info.code);
    return <>
        <KickedModal/>
        <MainMessage/>
        <LobbyPlayers/>
        <ContextCode/>

        <Info.Consumer>
            {info => info.isAdmin ? <StartModal/> : <></>}
        </Info.Consumer>
    </>
}

function ContextCode() {
    return (
        <Info.Consumer>
            {info => <MatchCode code={info.code}/>}
        </Info.Consumer>
    );
}

function KickedModal() {
    const [show, setShow] = useState(false);
    const navigate = useNavigate();

    return (
        <Info.Consumer>
            {
                info => {
                    if (info.hasBeenKicked)
                        setShow(true);
                    return <BaseModal
                        title={"¡Bad news!"}
                        body={"Sadly, you have been kicked from this match by its host"}
                        state={[show, setShow]}
                        onContinue={() => {
                            navigate("/");
                        }}
                        canCancel={false}
                        continueText={"I accept it"}
                    />
                }
            }
        </Info.Consumer>)
}

function startMatchRequest(code) {
    const a = async () => {
        try {
            await fetchAuthenticated(`/api/v1/matches/${code}/start`, "POST")
                .then(async response => await response.json());
        } catch (error) {
            console.log(error.message)
        }
    }
    a();
}

function StartModal() {
    const [startShow, setStartShow] = useState(false);

    return (
        <Info.Consumer>
            {
                info => {
                    return <><BaseModal
                        title={"Start Game"}
                        body={"¿Do you want to start the game?"}
                        state={[startShow, setStartShow]}
                        onContinue={startMatchRequest(info.code)}/>

                        <Button buttonType={ButtonType.primary} onClick={() => setStartShow(true)}
                                disabled={!(info.players.length > 1)}>START</Button>
                    </>

                }
            }
        </Info.Consumer>
    )
}
