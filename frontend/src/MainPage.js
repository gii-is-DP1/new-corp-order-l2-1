import AppNavbar from "./AppNavbar";
import Card from "./components/Card";
import PublicIcon from "@mui/icons-material/Public";
import GroupIcon from "@mui/icons-material/Group";
import Button, {buttonStyles} from "./components/Button";
import InsertLinkIcon from "@mui/icons-material/InsertLink";
import TextInput from "./components/TextInput";
import LockIcon from "@mui/icons-material/Lock";
import React from "react";
import {Text} from "./components/Text";

export function MainPage({}) {

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
        justifyContent: "space-evenly",
        alignItems: "center"
    }

    const cardStyle = {
    }

    const cardRowStyle = {
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "space-between",
        margin: "10px",
        gap: "30px"
    }

    return (
        <div style={{display: "flex", flexDirection: "column", height: "100%"}}>
            <AppNavbar/>

            <div style={content}>
                <div style={columnStyle}>
                    <Card style={cardStyle}
                          title={"Play now"}
                          subtitle={"Join public match"}
                          icon={<PublicIcon style={{fontSize: "45px"}}/>}
                    >
                        <section style={cardRowStyle}>
                            <Text>Game mode</Text>
                            <div style={{display: "flex", flexDirection: "row", gap: "15px"}}>
                                <Text>Normal</Text>
                                <Text>Quick</Text>
                            </div>
                        </section>
                        <section style={cardRowStyle}>
                            <Text>Number of players</Text>
                            <div style={{display: "flex", flexDirection: "row", gap: "15px"}}>
                                <Text>2<GroupIcon/></Text>
                                <Text>3<GroupIcon/></Text>
                                <Text>4<GroupIcon/></Text>
                            </div>
                        </section>
                        <div style={{display: "flex", flexDirection: "row", justifyContent: "center"}}>
                            <Button buttonText={"Play"} buttonColor={buttonStyles.secondary}></Button>
                        </div>
                    </Card>

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
                    <Card title={"Private game"}
                          subtitle={"Create a private match"}
                          icon={<LockIcon style={{fontSize: "45px"}}/>}
                          style={cardStyle}
                    >

                    </Card>
                </div>
            </div>
        </div>
    )
}


