import AppNavbar from "../AppNavbar";
import {black, grayDarker, white} from "../util/Colors";
import ProfilePicture from "../components/ProfilePicture";
import {Title} from "../components/Title";
import Button, {ButtonType} from "../components/Button";
import {Text} from "../components/Text";
import {PressableText} from "../components/PressableText";
import List from "../components/List";
import React, {useEffect, useState} from "react";
import ListLine from "../components/ListLine";
import {Subtitle} from "../components/Subtitle";
import fetchAuthenticated from "../util/fetchAuthenticated";
import {useNavigate, useParams} from "react-router-dom";
import tokenService from "../services/token.service";
import TextInput from "../components/TextInput";
import {buildErrorComponent, buildSuccessComponent} from "../util/formUtil";

export function ProfilePage() {
    const [userData, setUserData] = useState(null)
    const {username, select} = useParams()
    const navigate = useNavigate()
    const [formMessage, setFormMessage] = useState(<></>)

    if (!select) {
        navigate(`/user/${username}/lastMatches`)
    }

    const fetchUserData = async () => {
        try {
            setUserData(await fetchAuthenticated(`/api/v1/users/${username}`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            navigate('')
        }
    };

    useEffect(() => {
        fetchUserData()
    }, [select, username]);

    if (!userData) {
        return <></>
    }

    const content = {
        flex: 1,
        display: "flex",
        flexDirection: "row"
    }

    const columnStyle = {
        marginLeft: "auto",
        marginRight: "auto",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        gap: "15px"
    }

    const rowListStyle = {
        backgroundColor: black,
        display: "flex",
        flexDirection: "column",
        maxHeight: "500px",
        width: "800px",
        overflow: "auto",
        gap: "5px"
    }

    function isMe() {
        return userData ? userData.username.toUpperCase() === tokenService.getUser().username.toUpperCase() : ""
    }

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

    let matchItems = []
    for (let i = 1; i < 20; i++) {
        matchItems.push(
            <ListLine sideContent={(
                <Button style={{}} buttonType={ButtonType.secondaryLight}>
                    Spectate
                </Button>)}>
                <Subtitle>· Match #{i} |</Subtitle>
                <Subtitle>Match State |</Subtitle>
                <Subtitle>Num players</Subtitle>
            </ListLine>
        )
    }

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

    let statsItems = []
    for (let i = 1; i < 20; i++) {
        statsItems.push(
            <ListLine sideContent={(
                <Button style={{}} buttonType={ButtonType.secondaryLight}>
                    View Match
                </Button>)}>
                <Subtitle>· Stat #{i} |</Subtitle>
                <Subtitle> From match #{i + 3 * i * i}</Subtitle>
            </ListLine>
        )
    }
    console.log(tokenService.getUser())
    return (
        <div style={{display: "flex", flexDirection: "column", height: "100%", backgroundColor: black}}>

            <AppNavbar/>

            <div style={content}>
                <section style={columnStyle}>
                    <ProfilePicture url={userData.picture} style={{height: "150px", width: "150px"}}/>
                    <Title style={{color: white, fontSize: "35px"}}>{userData.username}</Title>
                    {isMe() &&
                        <Button onClick={() => navigate(`/user/${userData.username}/edit`)}
                                buttonType={ButtonType.primary}> Edit </Button>}
                    <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                        <Text style={{color: grayDarker}}>000 victories</Text>
                        <Text style={{color: grayDarker}}>000 matches played</Text>
                        <Text style={{color: grayDarker}}>more relevant stats...</Text>
                    </div>
                </section>
                <section style={columnStyle}>
                    <div style={{display: "flex", gap: "25px"}}>
                        <PressableText style={{color: white}}
                                       underlined={select === "lastMatches"}
                                       onClick={() => navigate(`/user/${userData.username}/lastMatches`)}>
                            Last matches
                        </PressableText>
                        <PressableText style={{color: white}}
                                       underlined={select === "friends"}
                                       onClick={() => navigate(`/user/${userData.username}/friends`)}>
                            Friends
                        </PressableText>
                        <PressableText style={{color: white}}
                                       underlined={select === "stats"}
                                       onClick={() => navigate(`/user/${userData.username}/stats`)}>
                            Stats
                        </PressableText>
                    </div>
                    <div>
                        {select === "lastMatches" &&
                            <List style={rowListStyle}>
                                {matchItems}
                            </List>}

                        {select === "friends" &&
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
                            </div>}

                        {select === "stats" &&
                            <List style={rowListStyle}>
                                {statsItems}
                            </List>}
                    </div>
                </section>
            </div>
        </div>
    )
}
