import List from "./List";
import ListLine from "./ListLine";
import {Text} from "./Text";
import React, {useEffect, useState} from "react";
import fetchAuthenticated from "../util/fetchAuthenticated";
import TextInput from "./TextInput";
import {useParams} from "react-router-dom";
import {set} from "msw";
import Button, {ButtonType} from "./Button";

export function Chat() {
    const style = {
        background: "#a3a3a3",
        width: "400px",
        flex: 3,
        display: "flex",
        transform: "translate(-30%,20%)",
        flexDirection: "column",
        position: "fixed",
        height: "80vh"
    }
    const [matchData, setMatchData] = useState(null)
    const {id} = useParams()

    const [isShown, setIsShown] = useState(false);
    const fetchMatchData = async () => {
        try {
            setMatchData((await fetchAuthenticated(`/api/v1/matches/${id}`, "GET")
                .then(async response => await response.json())));
        } catch (error) {
            console.log(error)
        }
    }

    useEffect(() => {
        fetchMatchData();
        const interval = setInterval(() => {
            fetchMatchData();
        }, 1500);

        return () => clearInterval(interval);
    }, []);

    if (!matchData) {
        return <></>
    }

    let messagesItems = matchData.messages?.map(message => {
        return (
            <ListLine>
                <div style={{display: "flex", flexDirection: "column", margin: "7px 7px"}}>
                    <Text>{message.sender}</Text>
                    <Text>{message.message}</Text>
                </div>
            </ListLine>
        )
    })

    async function sendMessage(message) {
        await fetchAuthenticated(`/api/v1/matches/${id}/chat`, "POST", {message: message})
    }

    return (
        <>
            <Button buttonType={ButtonType.primary} onClick={() => setIsShown(!isShown)}> Toggle chat</Button>
            <div style={{...style, overflow: "scroll", opacity: isShown ? 1 : 0}}>
                <List style={{flex: 1, overflow: "scroll"}}>
                    {messagesItems}
                </List>
                <TextInput placeholder={"Enter your message..."} onClick={sendMessage}></TextInput>
            </div>
        </>
    )
}
