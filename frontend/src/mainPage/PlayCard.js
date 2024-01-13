import React, {useEffect, useState} from "react";
import {black, dangerBackground, orange, successBackground} from "../util/Colors";
import fetchAuthenticated from "../util/fetchAuthenticated";
import ListLine from "../components/ListLine";
import QueueIcon from "@mui/icons-material/Queue";
import ProfilePicture from "../components/ProfilePicture";
import {Text} from "../components/Text";
import Card from "../components/Card";
import {PressableText} from "../components/PressableText";
import GroupIcon from "@mui/icons-material/Group";
import List from "../components/List";
import Button, {ButtonType} from "../components/Button";
import tokenService from "../services/token.service";

export function PlayCard({
                             title,
                             subtitle,
                             icon,
                             style,
                             privateGame = false,
                             navigate,
                             cardStyle,
                             cardContentStyle,
                             cardContentRowStyle
                         }) {

    let [userFriends, setUserFriends] = useState(null)
    const [gameMode, setGameMode] = useState("")
    const [players, setPlayers] = useState("")
    const [invitationRequest, setInvitationRequest] = useState([])

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
        setInterval(() => fetchUserFriends(), 5000)
    }, [])

    if (!userFriends) {
        return <></>
    }

    let friendsItems = userFriends?.map(friend => {
        return (
            <ListLine sideContent={!invitationRequest.includes(friend.username) && (
                <button onClick={() => setInvitationRequest([...invitationRequest, friend.username])}
                        style={{backgroundColor: "transparent", border: "none", padding: "3px"}}>
                    <QueueIcon style={{color: black}}/>
                </button>
            )}
            >
                <ProfilePicture url={friend.picture} style={{height: "30px", width: "30px"}}/>
                <Text>{friend.username} | </Text>
                {friend.online && <Text style={{color: successBackground}}>Online</Text>}
                {!friend.online && <Text style={{color: dangerBackground}}>Offline</Text>}
            </ListLine>
        )
    })

    async function createMatch(privateGame) {
        try {
            const append = privateGame ? "" : "/quick"
            const response = await fetchAuthenticated(`/api/v1/matches${append}?mode=${gameMode.toUpperCase()}&maxPlayers=${players}`, "POST")
                .then(response => response.json())

            if (privateGame) {
                let friends = invitationRequest.map(friend => `target=${friend}`).join("&")
                friends = invitationRequest ? `?${friends}` : ""
                await fetchAuthenticated(`/api/v1/matches/${response.matchCode}/invite${friends}`, "POST")
            }

            navigate(`/match/${response.matchCode}`)
        } catch (error) {
            console.log(error.message)
        }
    }

    function getColor(state, expected) {
        return state === expected ? orange : black;
    }

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
                        <PressableText color={getColor(gameMode, "normal")}
                                       onClick={() => setGameMode("normal")}>
                            Normal
                        </PressableText>
                        <PressableText color={getColor(gameMode, "quick")}
                                       onClick={() => setGameMode("quick")}>
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
