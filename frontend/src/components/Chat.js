import List from "./List";
import ListLine from "./ListLine";
import {Text} from "./Text";
import React from "react";
import fetchAuthenticated from "../util/fetchAuthenticated";
import TextInput from "./TextInput";

export function Chat({chatData, matchCode}) {

    let messages = chatData?.map(message => {
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
        await fetchAuthenticated(`/api/v1/matches/${matchCode}/chat`, "POST", message)
    }

    return (
        <div style={{maxWidth: "350px", backgroundColor: "transparent"}}>
            <List>
                {messages}
            </List>
            <TextInput placeholder={"Enter your message..."} onClick={sendMessage}></TextInput>
        </div>
    )
}
