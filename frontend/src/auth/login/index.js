import React from "react";
import tokenService from "../../services/token.service";
import Card from "../../components/Card";
import Button, {ButtonType} from "../../components/Button";
import LoginIcon from '@mui/icons-material/Login';
import FormInput from "../../components/FormInput";
import {Text} from "../../components/Text";

export default function Login() {

    const [message, setMessage] = React.useState();

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

    async function handleSubmit({values}) {
        const reqBody = values;
        setMessage(null);
        await fetch("/api/v1/users/login", {
            headers: {"Content-Type": "application/json"},
            method: "GET",
            body: JSON.stringify(reqBody),
        })
            .then(function (response) {
                if (response.status === 200) return response.json();
                else return Promise.reject("Invalid login attempt");
            })
            .then(function (data) {
                tokenService.setUser(data);
                tokenService.updateLocalAccessToken(data.token);
            })
            .catch((error) => {
                setMessage(error);
            });
    }

    return (
        <div style={content}>
            <div style={columnStyle}>
                <img src={"Images/New-corp-order-logo.png"} alt={"New Corp Order logo"}></img>
            </div>
            <div style={columnStyle}>
                <Card style={cardStyle}
                      title={"Login"}
                      subtitle={"Back again, huh?"}
                      icon={<LoginIcon style={{fontSize: "45px"}}/>}
                >
                    <div style={{margin: "15px"}}>
                        <form style={{display: 'flex', flexDirection: 'column'}}
                              onSubmit={(e) => {
                                  e.preventDefault();
                                  handleSubmit(e)
                              }}>
                            <FormInput name={"username"} placeholder={"Type your username here"}></FormInput>
                            <FormInput name={"password"} placeholder={"**********"} type={"password"}></FormInput>
                            <div style={buttonStyle}>
                                {message && <Text style={{textTransform: "none", color: "red"}}>{message}</Text>}
                                <Button buttonType={ButtonType.primary} type="submit">Login</Button>
                            </div>
                        </form>
                        <div style={{display: "flex", flexDirection: "row"}}>
                            <hr style={lineStyle}/>
                            <Text style={lineTextStyle}> Don't have an account yet? </Text>
                            <hr style={lineStyle}/>
                        </div>
                        <div style={buttonStyle}>
                            <a style={{textDecoration: "none"}} href={"/register"}>
                                <Button buttonType={ButtonType.secondaryLight}>
                                    Sign up instead
                                </Button>
                            </a>
                        </div>
                    </div>
                </Card>
            </div>
        </div>
    );

}
