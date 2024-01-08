import {MatchCode} from "./MatchCode";
import {MainMessage} from "./MainMessage";
import {LobbyPlayers} from "./LobbyPlayers";
import {Info} from "../Match";
import {useContext, useState} from "react";
import BaseModal from "../../components/BaseModal";
import { useNavigate } from "react-router-dom";


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
    const navigate = useNavigate();

    return (
        <Info.Consumer>
            {
                info =>
                {
                    if(info.hasBeenKicked)
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
        </Info.Consumer>
    )
}
