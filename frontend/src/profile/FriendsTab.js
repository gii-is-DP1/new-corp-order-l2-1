import fetchAuthenticated from "../util/fetchAuthenticated";
import {buildErrorComponent, buildSuccessComponent} from "../util/formUtil";
import React, {useState} from "react";
import ListLine from "../components/ListLine";
import Button, {ButtonType} from "../components/Button";
import ProfilePicture from "../components/ProfilePicture";
import {Text} from "../components/Text";
import List from "../components/List";
import {white} from "../util/Colors";
import TextInput from "../components/TextInput";

export function FriendsTab({userData, navigate, fetchUserData, isMe, rowListStyle}) {
    const [formMessage, setFormMessage] = useState(<></>)


    async function sendFriendRequest(value) {
        try {
            await fetchAuthenticated(`/api/v1/users/friendships/requests/${value}`, "POST")
            await fetchUserData()
            setFormMessage(buildSuccessComponent("Request sent successfully!"))
        } catch (error) {
            setFormMessage(buildErrorComponent(error.message))
        }
        setTimeout(() => setFormMessage(("")), 2000)
    }

    let friendsRequest = userData.receivedFriendshipRequests?.map(request => {
        const friend = request.user
        return (
            <ListLine sideContent={(
                <>
                    <Button
                        onClick={() => fetchAuthenticated(`/api/v1/users/friendships/requests/${friend.username}`, "DELETE")
                            .then(() => fetchUserData())}
                        buttonType={ButtonType.danger}>
                        Deny
                    </Button>
                    <Button
                        onClick={() => fetchAuthenticated(`/api/v1/users/friendships/requests/${friend.username}/accept`, "POST")
                            .then(() => fetchUserData())}
                        buttonType={ButtonType.success}>
                        Accept
                    </Button>
                </>)}
            >
                <ProfilePicture url={friend.picture} style={{height: "30px", width: "30px"}}/>
                <Text>{friend.username}</Text>

            </ListLine>
        )
    })

    let friendsItems = userData.friends?.map(request => {
        const friend = request.user
        return (
            <ListLine sideContent={(
                <>
                    <Button
                        onClick={() => fetchAuthenticated(`/api/v1/users/friendships/${friend.username}`, "DELETE")
                            .then(() => fetchUserData())}
                        buttonType={ButtonType.danger}>
                        Delete
                    </Button>
                    <Button onClick={() => navigate(`/user/${friend.username}`)} buttonType={ButtonType.secondaryLight}>
                        Visit profile
                    </Button>
                </>)}
            >
                <ProfilePicture url={friend.picture} style={{height: "30px", width: "30px"}}/>
                <Text>{friend.username}</Text>
            </ListLine>
        )
    })

    return (
        <div style={{width: "100%", display: "flex", flexDirection: "column"}}>

            {friendsRequest?.length !== 0 && isMe() &&
                <List style={{...rowListStyle, paddingBottom: "50px"}}>
                    <Text style={{color: white}}> 🔔 You have friendship requests from:</Text>
                    {friendsRequest}
                </List>
            }

            {isMe() &&
                <>
                    <TextInput
                        onClick={sendFriendRequest}
                        style={{margin: "5px", flex: "1"}}
                        placeholder={"Search an user to send a request"}>
                    </TextInput>
                    {formMessage}
                </>
            }

            <List style={rowListStyle}>
                {friendsItems}
            </List>

        </div>
    )
}
