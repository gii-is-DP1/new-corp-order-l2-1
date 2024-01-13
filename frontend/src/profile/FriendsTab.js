import fetchAuthenticated from "../util/fetchAuthenticated";
import {buildErrorComponent, buildSuccessComponent} from "../util/formUtil";
import React, {useEffect, useState} from "react";
import ListLine from "../components/ListLine";
import Button, {ButtonType} from "../components/Button";
import ProfilePicture from "../components/ProfilePicture";
import {Text} from "../components/Text";
import List from "../components/List";
import {dangerBackground, successBackground, white} from "../util/Colors";
import TextInput from "../components/TextInput";
import tokenService from "../services/token.service";

export function FriendsTab({userData, navigate, fetchUserData, isMe, rowListStyle}) {
    const [formMessage, setFormMessage] = useState(<></>)
    const [userFriends, setUserFriends] = useState(null)

    const fetchUserFriends = async () => {
        try {
            setUserFriends(await fetchAuthenticated(`/api/v1/users/${tokenService.getUser().username}/friends`, "GET")
                .then(response => response.json()));
        } catch (error) {
            console.log(error.message)
        }
    };

    useEffect(() => {
        fetchUserFriends()
    }, []);

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

    let friendsItems = userFriends?.map(request => {
        return (
            <ListLine sideContent={(
                <>
                    <Button
                        onClick={() => fetchAuthenticated(`/api/v1/users/friendships/${request.username}`, "DELETE")
                            .then(() => fetchUserData())}
                        buttonType={ButtonType.danger}>
                        Delete
                    </Button>
                    <Button onClick={() => navigate(`/user/${request.username}`)}
                            buttonType={ButtonType.secondaryLight}>
                        Visit profile
                    </Button>
                </>)}
            >
                <ProfilePicture url={request.picture} style={{height: "30px", width: "30px"}}/>
                <Text>{request.username} | </Text>
                {request.online && <Text style={{color: successBackground}}>Online</Text>}
                {!request.online && <Text style={{color: dangerBackground}}>Offline</Text>}
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
