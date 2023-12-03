import React, {useState} from "react";
import {Alert} from "reactstrap";
import FormGenerator from "../../components/formGenerator/formGenerator";
import tokenService from "../../services/token.service";
//import "../../static/css/auth/authButton.css";
import {loginFormInputs} from "./form/loginFormInputs";
import Card from "../../components/Card";
import TextInput from "../../components/TextInput";
import Button from "../../components/Button";
import LoginIcon from '@mui/icons-material/Login';
import PublicIcon from "@mui/icons-material/Public";
import Form from "../../components/Form";
import FormInput from "../../components/FormInput";
import {ButtonType} from "../../components/Button";
import {Text} from "../../components/Text";

export default function Login() {
    const [message, setMessage] = useState(null)
    const loginFormRef = React.createRef();

    const containerStyle = {
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-evenly",
        alignItems: "space-between",
        backgroundImage: "url(/Images/BackgroundImage.svg)",
        backgroundSize: "cover",
        backgroundPosition: "bottom",
        height: "100%",
    }

    const imageStyle = {
        height:"180px",
        width:"260px",
    }

    const cardStyle = {
        width: "500px",
        height: "410px",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        margin: "8px",
        flex:0.8

    }
    const divStyle = {
        display: "flex",
        flexDirection: "row",
        margin: "10px"
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

    const formStyle = {
        display: 'flex',
        flexDirection: 'column',
    }

    const cardContainerStyle = {
        margin: "15px",
    }



    async function handleSubmit({values}) {

        const reqBody = values;
        setMessage(null);
        await fetch("/api/v1/auth/signin", {
            headers: {"Content-Type": "application/json"},
            method: "POST",
            body: JSON.stringify(reqBody),
        })
            .then(function (response) {
                if (response.status === 200) return response.json();
                else return Promise.reject("Invalid login attempt");
            })
            .then(function (data) {
                tokenService.setUser(data);
                tokenService.updateLocalAccessToken(data.token);
                window.location.href = "/dashboard";
            })
            .catch((error) => {
                setMessage(error);
            });
    }



    async function login({formData}) {
        console.log(formData);
    }

    const formInputs = [
        <FormInput name={"email"} placeholder={"Email"}></FormInput>,
        <FormInput name={"password"} placeholder={"Password"}></FormInput>
    ]

    return (
    <div style={containerStyle}>
        <div style = {{alignItems:"center", display:"flex", }}>
            <img style={imageStyle} src = {"Images/New-corp-order-logo.png"} alt={"New Corp Order logo"}></img>
        </div>
        <div style = {{alignItems:"center", display:"flex", justifyContent:"right"}}>
            <Card title={"LOGIN"} subtitle={"BACK AGAIN, HUH?"} style = {cardStyle} icon={<LoginIcon style={{fontSize: "45px"}}/>}>
                <div style={cardContainerStyle}>
                    <form onSubmit={handleSubmit} style={formStyle}>
                        <FormInput name={"email"} placeholder={"Type your email here"}></FormInput>
                        <FormInput name={"password"} placeholder={"**********"}></FormInput>
                        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', paddingTop:"10px" }}>
                            <Button buttonType={ButtonType.primary} type="submit">LOGIN</Button>
                        </div>
                    </form>
                    <div style={divStyle}>
                        <hr style = {lineStyle} />
                        <Text style = {lineTextStyle}> Don't have an account yet? </Text>
                        <hr style = {lineStyle} />
                    </div>
                    <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>
                        <Button buttonType={ButtonType.secondaryLight}>SIGN UP INSTEAD</Button>
                    </div>
                </div>
            </Card>
        </div>
    </div>
    );

}
