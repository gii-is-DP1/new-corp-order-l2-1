import AppNavbar from "./AppNavbar";
import Card from "./components/Card";
import PublicIcon from "@mui/icons-material/Public";
import GroupIcon from "@mui/icons-material/Group";
import Button, {buttonStyles} from "./components/Button";
import InsertLinkIcon from "@mui/icons-material/InsertLink";
import TextInput from "./components/TextInput";
import LockIcon from "@mui/icons-material/Lock";
import React from "react";

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
                          title={"PLAY NOW"}
                          subtitle={"JOIN PUBLIC MATCH"}
                          icon={<PublicIcon style={{fontSize: "45px"}}/>}
                    >
                        <section style={cardRowStyle}>
                            <p>GAME MODE</p>
                            <div style={{display: "flex", flexDirection: "row", gap: "15px"}}>
                                <p>NORMAL</p>
                                <p>QUICK</p>
                            </div>
                        </section>
                        <section style={cardRowStyle}>
                            <p>NUMBER OF PLAYERS</p>
                            <div style={{display: "flex", flexDirection: "row", gap: "15px"}}>
                                <p>1<GroupIcon/></p>
                                <p>2<GroupIcon/></p>
                                <p>3<GroupIcon/></p>
                            </div>
                        </section>
                        <div style={{display: "flex", flexDirection: "row", justifyContent: "center"}}>
                            <Button buttonText={"PLAY"} buttonColor={buttonStyles.secondary}></Button>
                        </div>
                    </Card>

                    <Card style={cardStyle}
                          title={"JOIN GAME"}
                          subtitle={"WITH MATCH CODE"}
                          icon={<InsertLinkIcon style={{fontSize: "45px"}}/>}
                    >
                        <div style={{margin: "10px"}}>
                            <TextInput placeholder={"ENTER MATCH CODE..."} onClick={() => {
                            }}></TextInput>
                        </div>
                    </Card>
                </div>

                <div style={columnStyle}>
                    <Card title={"PRIVATE GAME"}
                          subtitle={"PLAY SOLO OR WITH FRIENDS"}
                          icon={<LockIcon style={{fontSize: "45px"}}/>}
                          style={cardStyle}
                    >

                    </Card>
                </div>
            </div>
        </div>
    )
}


