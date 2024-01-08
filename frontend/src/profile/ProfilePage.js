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
import {FriendsTab} from "./FriendsTab";
import {propics} from "../match/data/MatchEnums";

export function ProfilePage() {
    const [userData, setUserData] = useState(null)
    const {username, select} = useParams()
    const navigate = useNavigate()

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

    function isMe() {
        return userData ? userData.username.toUpperCase() === tokenService.getUser().username.toUpperCase() : ""
    }

    function logout() {
        tokenService.removeUser();
        window.location.href = "/";
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

    let matchItems = []
    for (let i = 1; i < 20; i++) {
        matchItems.push(
            <ListLine sideContent={(
                <Button style={{}} buttonType={ButtonType.secondaryLight}>
                    View Stats
                </Button>)}>
                <Subtitle>· Match #{i} |</Subtitle>
                <Subtitle>Match State |</Subtitle>
                <Subtitle>Num players</Subtitle>
            </ListLine>
        )
    }

    let achievementsItems = []
    for (let i = 1; i < 20; i++) {
        achievementsItems.push(
            <ListLine sideContent={<Button buttonType={ButtonType.primary}>Logrado? si: no </Button>}>
                <Subtitle>· Achievement #{i} |</Subtitle>
                <Subtitle> Blablabla</Subtitle>
            </ListLine>
        )
    }

    return (
        <div style={{display: "flex", flexDirection: "column", height: "100%", backgroundColor: black}}>

            <AppNavbar/>

            <div style={content}>
                <section style={columnStyle}>
                    <ProfilePicture url={propics[userData.picture]} style={{height: "150px", width: "150px"}}/>
                    <Title style={{color: white, fontSize: "35px"}}>{userData.username}</Title>
                    {isMe() && <div style={{display: "flex", flexDirection: "row", gap: "5px"}}>
                        <Button onClick={() => navigate(`/user/${userData.username}/edit`)}
                                buttonType={ButtonType.primary}>
                            Edit
                        </Button>
                        <Button onClick={() => logout()}
                                buttonType={ButtonType.danger}>
                            Logout
                        </Button>
                    </div>}
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
                                       underlined={select === "achievements"}
                                       onClick={() => navigate(`/user/${userData.username}/achievements`)}>
                            Achievements
                        </PressableText>
                    </div>
                    <div>
                        {select === "lastMatches" &&
                            <List style={rowListStyle}>
                                {matchItems}
                            </List>}

                        {select === "friends" &&
                            <FriendsTab userData={userData}
                                        navigate={navigate}
                                        fetchUserData={fetchUserData}
                                        isMe={isMe}
                                        rowListStyle={rowListStyle}
                            />}

                        {select === "achievements" &&
                            <List style={rowListStyle}>
                                {achievementsItems}
                            </List>}
                    </div>
                </section>
            </div>
        </div>
    )
}
