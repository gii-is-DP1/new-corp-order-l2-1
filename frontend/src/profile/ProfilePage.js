import AppNavbar from "../AppNavbar";
import {black, grayDarker, white} from "../util/Colors";
import ProfilePicture from "../components/ProfilePicture";
import {Title} from "../components/Title";
import Button, {ButtonType} from "../components/Button";
import {Text} from "../components/Text";
import {PressableText} from "../components/PressableText";
import {List} from "reactstrap";
import React, {useState} from "react";
import ListLine from "../components/ListLine";
import {Subtitle} from "../components/Subtitle";

export function ProfilePage() {
    const [selected, setSelected] = useState("lastMatches")

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
                    Spectate
                </Button>)}>
                <Subtitle>· Match #{i} |</Subtitle>
                <Subtitle>Match State |</Subtitle>
                <Subtitle>Num players</Subtitle>
            </ListLine>
        )
    }

    let friendsItems = []
    for (let i = 1; i < 20; i++) {
        friendsItems.push(
            <ListLine sideContent={(
                <Button style={{}} buttonType={ButtonType.secondaryLight}>
                    Visit profile
                </Button>)}
            >
                <ProfilePicture style={{height: "30px", width: "30px"}}/>
                <Text>AmigoAmigui {i}</Text>
            </ListLine>
        )
    }

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

    return (
        <div style={{display: "flex", flexDirection: "column", height: "100%", backgroundColor: black}}>

            <AppNavbar/>

            <div style={content}>
                <section style={columnStyle}>
                    <ProfilePicture style={{height: "150px", width: "150px"}}/>
                    <Title style={{color: white, fontSize: "35px"}}>Username</Title>
                    <Button buttonType={ButtonType.primary}> Edit </Button>
                    <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                        <Text style={{color: grayDarker}}>000 victories</Text>
                        <Text style={{color: grayDarker}}>000 matches played</Text>
                        <Text style={{color: grayDarker}}>more relevant stats...</Text>
                    </div>
                </section>
                <section style={columnStyle}>
                    <div style={{display: "flex", gap: "25px"}}>
                        <PressableText style={{color: white}}
                                       underlined={selected === "lastMatches"}
                                       onClick={() => setSelected("lastMatches")}>
                            Last matches
                        </PressableText>
                        <PressableText style={{color: white}}
                                       underlined={selected === "friends"}
                                       onClick={() => setSelected("friends")}>
                            Friends
                        </PressableText>
                        <PressableText style={{color: white}}
                                       underlined={selected === "stats"}
                                       onClick={() => setSelected("stats")}>
                            Stats
                        </PressableText>
                    </div>
                    <div>
                        {selected === "lastMatches" &&
                            <List style={rowListStyle}>
                                {matchItems}
                            </List>}
                        {selected === "friends" &&
                            <List style={rowListStyle}>
                                {friendsItems}
                            </List>}
                        {selected === "stats" &&
                            <List style={rowListStyle}>
                                {statsItems}
                            </List>}
                    </div>
                </section>
            </div>
        </div>
    )
}
