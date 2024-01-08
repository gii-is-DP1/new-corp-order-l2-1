import {MatchCode} from "./MatchCode";
import {MainMessage} from "./MainMessage";
import {LobbyPlayers} from "./LobbyPlayers";
import {Info} from "../Match";
import {useContext, useState} from "react";
import BaseModal from "../../components/BaseModal";
import fetchAuthenticated from "../../util/fetchAuthenticated";
import Button, {ButtonType} from "../../components/Button";
import { useNavigate } from "react-router-dom";


export function Lobby() {
    return <>
        <KickedModal/>
        <MainMessage/>
        <LobbyPlayers/>
        <ContextCode/>
        <StartModal/>

    </>
}

function ContextCode() {
    return (
        <Info.Consumer>
            {info => <MatchCode code={info.code}/>}
        </Info.Consumer>
    );
}

function KickedModal(){
    const [show, setShow] = useState(false);
    const navigate = useNavigate();
    if(info.hasBeenKicked)
    setShow(true);
    return (
        <Info.Consumer>
            {
                info =>
                {
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

function StartModal(){
    const [startShow, setStartShow] = useState(false);
    
    

    return (
        <Info.Consumer>
            {
                info =>
                {
                    return <><BaseModal
                        title={<h2>START GAME</h2>}
                        body={<p>Do you want to start the game</p>}
                        state={[startShow, setStartShow]}
                        onContinue={async() => {
                            try {
                                await fetchAuthenticated(`/api/v1/matches/${info.code}/start`, "POST")
                                    .then(async response => await response.json());
                            } catch (error) {
                                console.log(error.message)
                            }
                        }
                        }/>

                        <Button buttonType={ButtonType.primary} onClick={() => setStartShow(true)} disabled={!(info.players.length > 1)}>START</Button>
                        </>

                }
            }
        </Info.Consumer>
    )
}
