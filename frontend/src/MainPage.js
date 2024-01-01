import AppNavbar from "./AppNavbar";
import Card from "./components/Card";
import PublicIcon from "@mui/icons-material/Public";
import GroupIcon from "@mui/icons-material/Group";
import Button, {ButtonType} from "./components/Button";
import InsertLinkIcon from "@mui/icons-material/InsertLink";
import TextInput from "./components/TextInput";
import LockIcon from "@mui/icons-material/Lock";
import React, {useEffect, useState} from "react";
import {Text} from "./components/Text";
import List from "./components/List";
import ListLine from "./components/ListLine";
import QueueIcon from '@mui/icons-material/Queue';
import {black, orange} from "./util/Colors";
import {PressableText} from "./components/PressableText";
import fetchAuthenticated from "./util/fetchAuthenticated";
import tokenService from "./services/token.service";
import ProfilePicture from "./components/ProfilePicture";
import {useNavigate} from "react-router-dom";
import {buildErrorComponent} from "./util/formUtil";

export function MainPage() {
    const [userFriends, setUserFriends] = useState(null)
    const [formMessage, setFormMessage] = useState(<></>)
    const navigate = useNavigate()

    const fetchUserFriends = async () => {
        try {
            setUserFriends(await fetchAuthenticated(`/api/v1/users/${tokenService.getUser().username}/friends`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            console.log(error.message)
        }
    };

    useEffect(() => {
        fetchUserFriends()
    }, [])

    if (!userFriends) {
        return <></>
    }

    const content = {
        flex: 1,
        display: "flex",
        flexDirection: "row",
        backgroundImage: "url(/Images/BackgroundImage.svg)",
        backgroundSize: "cover",
        backgroundPositionY: "bottom"
    }

    const columnStyle = {
        marginLeft: "auto",
        marginRight: "auto",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-evenly",
        alignItems: "center"
    }

    const cardStyle = {
        overflowY: "auto"
    }

    const cardContentStyle = {
        height: "100%",
        display: "flex",
        flexDirection: "column"
    }

    const cardContentRowStyle = {
        margin: "10px",
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "space-between",
        gap: "30px"
    }

    function PlayCard({title, subtitle, icon, style, privateGame = false}) {
        const [gamemode, setGamemode] = useState("")
        const [players, setPlayers] = useState("")

        function getColor(state, expected) {
            return state === expected ? orange : black;
        }

        async function createMatch(privateGame) {
            try {
                const append = privateGame ? "" : "/quick"
                await fetchAuthenticated(`/api/v1/matches${append}?mode=${gamemode.toUpperCase()}&maxPlayers=${players}`, "POST")
                    .then(response => response.json())
                    .then((response) => navigate(`/match/${response.matchCode}`))
            } catch (error) {
                console.log(error.message)
            }
        }

        let friendsItems = userFriends?.map(friend => {
            return (
                <ListLine sideContent={(
                    <button style={{backgroundColor: "transparent", border: "none", padding: "3px"}}>
                        <QueueIcon style={{color: black}}/>
                    </button>)}
                >
                    <ProfilePicture url={friend.picture} style={{height: "30px", width: "30px"}}/>
                    <Text>{friend.username}</Text>
                </ListLine>
            )
        })

        return (
            <Card style={{...cardStyle, ...style}}
                  title={title}
                  subtitle={subtitle}
                  icon={icon}
            >
                <div style={cardContentStyle}>
                    <section style={cardContentRowStyle}>
                        <Text>Game mode</Text>
                        <div style={{display: "flex", flexDirection: "row", gap: "15px"}}>
                            <PressableText color={getColor(gamemode, "normal")}
                                           onClick={() => setGamemode("normal")}>
                                Normal
                            </PressableText>
                            <PressableText color={getColor(gamemode, "quick")}
                                           onClick={() => setGamemode("quick")}>
                                Quick
                            </PressableText>
                        </div>
                    </section>
                    <section style={cardContentRowStyle}>
                        <Text>Number of players</Text>
                        <div style={{display: "flex", flexDirection: "row", gap: "15px"}}>
                            <PressableText color={getColor(players, "2")}
                                           onClick={() => setPlayers("2")}>
                                2<GroupIcon/>
                            </PressableText>
                            <PressableText color={getColor(players, "3")}
                                           onClick={() => setPlayers("3")}>
                                3<GroupIcon/>
                            </PressableText>
                            <PressableText color={getColor(players, "4")}
                                           onClick={() => setPlayers("4")}>
                                4<GroupIcon/>
                            </PressableText>
                        </div>
                    </section>
                    {privateGame &&
                        <section style={cardContentRowStyle}>
                            <List style={{height: "256px"}}>
                                {friendsItems}
                                {friendsItems.length === 0 &&
                                    <div style={{
                                        display: "flex",
                                        flexDirection: "column",
                                        alignItems: "center",
                                        justifyContent: "center"
                                    }}>
                                        <Text>
                                            There is no one to invite here...
                                        </Text>
                                        <img src={"https://media.giphy.com/media/ISOckXUybVfQ4/giphy.gif"}
                                             alt={"SpongeBob super sad"}
                                             style={{
                                                 height: "220px",
                                                 width: "300px",
                                                 borderRadius: "5px",
                                                 opacity: "0.9"
                                             }}/>
                                    </div>
                                }
                            </List>
                        </section>
                    }
                    <section style={{...cardContentRowStyle, justifyContent: "center"}}>
                        <Button onClick={() => privateGame ? createMatch(privateGame) : createMatch()}
                                buttonType={ButtonType.secondaryLight}>
                            Play
                        </Button>
                    </section>
                </div>
            </Card>)
    }

    async function joinGameRequest(value) {
        try {
            await fetchAuthenticated(`/api/v1/matches/${value}/join`, "POST")
                .then(() => navigate(`match/${value}`))
        } catch (error) {
            setFormMessage(buildErrorComponent(error.message))
        }
        setTimeout(() => setFormMessage(("")), 5000)
    }

    return (
        <div style={{display: "flex", flexDirection: "column", height: "100%"}}>
            <AppNavbar/>

            <div style={content}>
                <div style={columnStyle}>
                    <PlayCard title={"Play now"}
                              subtitle={"Join public match"}
                              icon={<PublicIcon style={{fontSize: "45px"}}/>}
                    />
                    <Card style={cardStyle}
                          title={"Join game"}
                          subtitle={"With match code"}
                          icon={<InsertLinkIcon style={{fontSize: "45px"}}/>}
                    >
                        <div style={{margin: "10px"}}>
                            <TextInput placeholder={"ENTER MATCH CODE..."} onClick={joinGameRequest}></TextInput>
                            {formMessage}
                        </div>
                    </Card>
                </div>

                <div style={columnStyle}>
                    <PlayCard title={"Private game"}
                              subtitle={"Create a private match"}
                              icon={<LockIcon style={{fontSize: "45px"}}/>}
                              style={cardStyle}
                              privateGame={true}
                    />
                </div>
            </div>
        </div>
    )
}
