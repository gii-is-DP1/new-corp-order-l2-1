import React, {useEffect, useState} from "react";
import {black, orange} from "../util/Colors";
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
    const [gamemode, setGamemode] = useState("")
    const [players, setPlayers] = useState("")

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
