import AppNavbar from "./AppNavbar";
import Card from "./components/Card";
import PublicIcon from "@mui/icons-material/Public";
import GroupIcon from "@mui/icons-material/Group";
import Button, {ButtonType} from "./components/Button";
import InsertLinkIcon from "@mui/icons-material/InsertLink";
import TextInput from "./components/TextInput";
import LockIcon from "@mui/icons-material/Lock";
import React from "react";
import {Text} from "./components/Text";
import List from "./components/List";
import ListLine from "./components/ListLine";
import QueueIcon from '@mui/icons-material/Queue';
import {black} from "./util/Colors";

export function MainPage() {

    const content = {
        flex: 1,
        display: "flex",
        flexDirection: "row",
        backgroundImage: "url(/Images/BackgroundImage.svg)",
        backgroundSize: "cover",
        backgroundPosition: "center"
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

    let items = []
    for (let i = 0; i < 20; i++) {
        items.push(
            <ListLine iconSrc={"/Images/Consultants/Dealmaker.png"}
                      sideContent={(
                          <button style={{backgroundColor: "transparent", border: "none", padding: "3px"}}>
                              <QueueIcon style={{color: black}}/>
                          </button>)}
            >
                <Text>AmigoAmigui{i}</Text>
            </ListLine>
        );
    }

    function PlayCard({title, subtitle, icon, style, privateGame = false}) {
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
                            <Text>Normal</Text>
                            <Text>Quick</Text>
                        </div>
                    </section>
                    <section style={cardContentRowStyle}>
                        <Text>Number of players</Text>
                        <div style={{display: "flex", flexDirection: "row", gap: "15px"}}>
                            <Text>2<GroupIcon/></Text>
                            <Text>3<GroupIcon/></Text>
                            <Text>4<GroupIcon/></Text>
                        </div>
                    </section>
                    {privateGame &&
                        <section style={cardContentRowStyle}>
                            <List style={{height: "256px"}}>
                                {items}
                            </List>
                        </section>
                    }
                    <section style={{...cardContentRowStyle, justifyContent: "center"}}>
                        <Button buttonType={ButtonType.secondaryLight}>
                            Play
                        </Button>
                    </section>
                </div>
            </Card>)
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
                            <TextInput placeholder={"ENTER MATCH CODE..."} onClick={() => {
                            }}></TextInput>
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
