//import "../../static/css/auth/authButton.css";
//import "../../static/css/auth/authPage.css";
import tokenService from "../../services/token.service";
import React, {useRef, useState} from "react";
import Card from "../../components/Card";
import LoginIcon from "@mui/icons-material/Login";
import FormInput from "../../components/FormInput";
import Button, {ButtonType} from "../../components/Button";
import {Text} from "../../components/Text";

export default function Register() {
    let [type, setType] = useState(null);
    let [authority, setAuthority] = useState(null);
    let [message, setMessage] = useState(null);

    const content = {
        height: "100%",
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-evenly",
        alignItems: "space-between",
        backgroundImage: "url(/Images/BackgroundImage.svg)",
        backgroundSize: "cover",
        backgroundPositionY: "bottom"
    }

    const columnStyle = {
        display: "flex",
        justifyContent: "center",
        alignItems: "center"
    }

    const cardStyle = {
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        margin: "15px"
    }

    const lineTextStyle = {
        whiteSpace: "nowrap",
        paddingTop: "5px",
        fontSize: "15px",
        paddingLeft: "10px",
        paddingRight: "10px",
    }

    const lineStyle = {
        flex: "1",
        border: "none",
        borderTop: "2px solid #2C2C2C"
    }

    const buttonStyle = {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        margin: "10px"
    }

    function handleSubmit(body) {
        let state = "";

        fetch("/api/v1/auth/signup", {
            headers: {"Content-Type": "application/json"},
            method: "POST",
            body: JSON.stringify(body),
        })
            .then(function (response) {
                if (response.status === 200) {
                    const loginRequest = {
                        username: body.username,
                        password: body.password,
                        email: body.email,
                    };

                    fetch("/api/v1/auth/login", {
                        headers: {"Content-Type": "application/json"},
                        method: "POST",
                        body: JSON.stringify(loginRequest),
                    })
                        .then(function (response) {
                            if (response.status === 200) {
                                state = "200";
                                return response.json();
                            } else {
                                state = "";
                                return response.json();
                            }
                        })
                        .then(function (data) {
                            if (state !== "200") alert(data.message);
                            else {
                                tokenService.setUser(data);
                                tokenService.updateLocalAccessToken(data.token);
                                window.location.href = "/";
                            }
                        })
                        .catch((error) => {
                            setMessage(error);
                        });
                }
            })
            .catch((message) => {
                setMessage(message);
            });
    }

    return (
        <div style={content}>
            <div style={columnStyle}>
                <img src={"Images/New-corp-order-logo.png"} alt={"New Corp Order logo"}></img>
            </div>
            <div style={columnStyle}>
                <Card style={cardStyle}
                      title={"sign up"}
                      subtitle={"Feeling like playing?"}
                      icon={<LoginIcon style={{fontSize: "45px"}}/>}
                >
                    <div style={{margin: "15px"}}>
                        <form style={{display: 'flex', flexDirection: 'column'}}
                              onSubmit={(e) => {
                                  e.preventDefault();
                                  handleSubmit({
                                      username: e.target.username.value,
                                      password: e.target.password.value,
                                      email: e.target.email.value
                                  });
                              }}>
                            <FormInput name={"email"} placeholder={"Type your email here"}></FormInput>
                            <FormInput name={"username"} placeholder={"Type your username here"}></FormInput>
                            <FormInput name={"password"} placeholder={"**********"} type={"password"}></FormInput>
                            <div style={buttonStyle}>
                                {message && <Text style={{textTransform: "none", color: "red"}}>{message}</Text>}
                                <Button buttonType={ButtonType.primary} type="submit">Register</Button>
                            </div>
                        </form>
                        <div style={{display: "flex", flexDirection: "row"}}>
                            <hr style={lineStyle}/>
                            <Text style={lineTextStyle}> Do you have an account? </Text>
                            <hr style={lineStyle}/>
                        </div>
                        <div style={buttonStyle}>
                            <a style={{textDecoration: "none"}} href={"/login"}>
                                <Button buttonType={ButtonType.secondaryLight}>
                                    Log in instead
                                </Button>
                            </a>
                        </div>
                    </div>
                </Card>
            </div>
        </div>
    );
}
