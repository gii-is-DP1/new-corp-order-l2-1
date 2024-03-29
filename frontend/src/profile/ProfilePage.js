import AppNavbar from "../AppNavbar";
import {black, gray, grayDarker, white} from "../util/Colors";
import ProfilePicture from "../components/ProfilePicture";
import {Title} from "../components/Title";
import Button, {ButtonType} from "../components/Button";
import {Text} from "../components/Text";
import {PressableText} from "../components/PressableText";
import React, {useEffect, useState} from "react";
import fetchAuthenticated from "../util/fetchAuthenticated";
import {useNavigate, useParams} from "react-router-dom";
import tokenService from "../services/token.service";
import {FriendsTab} from "./FriendsTab";
import {propics} from "../match/data/MatchEnums";
import {LastMatchesTab} from "./LastMatchesTab";
import {AchievementsTab} from "./achievement/AchievementsTab";
import {EditProfileTab} from "./EditProfileTab";
import {ChangePasswordTab} from "./ChangePasswordTab";

export function ProfilePage() {
    const [userData, setUserData] = useState(null)
    const [achievementsData, setAchievementsData] = useState(null)
    const [completedAchievementsData, setCompletedAchievementsData] = useState(null)
    const {username, select} = useParams()
    const navigate = useNavigate()
    const [userStats, setUserStats] = useState(null)

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

    const fetchAchievementsData = async () => {
        try {
            setAchievementsData(await fetchAuthenticated(`/api/v1/achievements?name=`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            navigate('')
        }
    };

    const fetchCompletedAchievementsData = async () => {
        try {
            setCompletedAchievementsData(await fetchAuthenticated(`/api/v1/achievements/completed/${username}`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            navigate('')
        }
    };

    const fetchUserStats = async () => {
        try {
            setUserStats(await fetchAuthenticated(`/api/v1/players/${username}/stats`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            console.log(error)
        }
    }

    useEffect(() => {
        fetchUserData()
        fetchAchievementsData()
        fetchCompletedAchievementsData()
        fetchUserStats()
    }, [select, username]);

    if (!userData || !userStats) {
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
                    {isMe() && <div style={{alignItems: "none", display: "flex"}}>
                        <PressableText
                            style={{color: gray, fontSize: "18px", textTransform: "none", textDecoration: "underline"}}
                            onClick={() => navigate(`/user/${username}/editPassword`)}>
                            Change Password
                        </PressableText>
                    </div>}
                    <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                        <Text style={{color: grayDarker}}>{userStats.totalMatches} matches played</Text>
                        <Text style={{color: grayDarker}}>{userStats.wins} victories</Text>
                        <Text style={{color: grayDarker}}>{userStats.ties} ties</Text>
                        <Text style={{color: grayDarker}}>{userStats.loses} loses</Text>
                    </div>
                </section>
                <section style={columnStyle}>
                    {select !== "edit" && select !== "editPassword" && <div style={{display: "flex", gap: "25px"}}>
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
                    </div>}
                    <div>
                        {select === "lastMatches" &&
                            <LastMatchesTab username={username}
                                            navigate={navigate}
                                            rowListStyle={rowListStyle}
                            />}

                        {select === "friends" &&
                            <FriendsTab userData={userData}
                                        navigate={navigate}
                                        fetchUserData={fetchUserData}
                                        isMe={isMe}
                                        rowListStyle={rowListStyle}
                            />}

                        {select === "achievements" &&
                            <AchievementsTab achievementsCompleatedData={completedAchievementsData}
                                             allAchievementsData={achievementsData}
                                             isMe={isMe()}
                                             rowListStyle={rowListStyle}
                                             username={username}

                            />}

                        {select === "edit" &&
                            <EditProfileTab userData={userData}
                                            username={username}
                                            navigate={navigate}
                                            oldEmail={userData.email}


                            />}

                        {select === "editPassword" &&
                            <ChangePasswordTab userData={userData}
                                               username={username}
                                               navigate={navigate}
                                               oldEmail={userData.email}
                                               editPassword={true}

                            />}

                    </div>
                </section>
            </div>
        </div>
    )
}
