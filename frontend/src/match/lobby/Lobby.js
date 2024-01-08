import {MatchCode} from "./MatchCode";
import {MainMessage} from "./MainMessage";
import {LobbyPlayers} from "./LobbyPlayers";
import {Info} from "../Match";
import {useContext, useState} from "react";
import BaseModal from "../../components/BaseModal";


export function Lobby() {
    return <>
        <KickedModal/>
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

function KickedModal(){
    const [show, setShow] = useState(false);



    return (
        <Info.Consumer>
            {
                info =>
                {//
                    if(info.hasBeenKicked)
                        setShow(true);
                   return <BaseModal
                        title={<h2>¡Bad news!</h2>}
                        body={<p>Sadly, you have been kicked from this match by its host</p>}
                        state={[show, setShow]}
                        onContinue={() => {
                            info.hasBeenKicked = false;
                        }}
                    />
                }
            }
        </Info.Consumer>
    )
}

function StartModal(){
    const [show, setShow] = useState(false);


    return (
        <Info.Consumer>
            {
                info =>
                {
                    if(info.players.length > 1)
                        setShow(true);
                    return <BaseModal
                        title={<h2>START GAME</h2>}
                        body={<p>Do you want to start the game</p>}
                        state={[show, setShow]}
                        onContinue={() => {

                        }}
                    />
                }
            }
        </Info.Consumer>
    )
}
