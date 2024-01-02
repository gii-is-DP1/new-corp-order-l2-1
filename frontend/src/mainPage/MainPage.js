import AppNavbar from "../AppNavbar";
import Card from "../components/Card";
import PublicIcon from "@mui/icons-material/Public";
import InsertLinkIcon from "@mui/icons-material/InsertLink";
import TextInput from "../components/TextInput";
import LockIcon from "@mui/icons-material/Lock";
import React, {useState} from "react";
import fetchAuthenticated from "../util/fetchAuthenticated";
import {useNavigate} from "react-router-dom";
import {buildErrorComponent} from "../util/formUtil";
import {PlayCard} from "./PlayCard"
import {Notifications} from "./Notifications";

export function MainPage() {
    const [formMessage, setFormMessage] = useState(<></>)
    const navigate = useNavigate()

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
            <Notifications/>
            <div style={content}>
                <div style={columnStyle}>
                    <PlayCard title={"Play now"}
                              subtitle={"Join public match"}
                              icon={<PublicIcon style={{fontSize: "45px"}}/>}
                              style={cardStyle}
                              navigate={navigate}
                              cardContentStyle={cardContentStyle}
                              cardContentRowStyle={cardContentRowStyle}
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
                              navigate={navigate}
                              cardContentStyle={cardContentStyle}
                              cardContentRowStyle={cardContentRowStyle}
                    />
                </div>
            </div>
        </div>
    )
}
