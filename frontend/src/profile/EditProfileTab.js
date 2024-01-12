import {Title} from "../components/Title";
import {black, dangerBackground, grayDarker, orange, white} from "../util/Colors";
import FormInput from "../components/FormInput";
import {Text} from "../components/Text";
import Button, {ButtonType} from "../components/Button";
import React from "react";
import fetchAuthenticatedWithBody from "../util/fetchAuthenticatedWithBody";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import tokenService from "../services/token.service";

export function EditProfileTab({username, oldEmail, userData, navigate}) {
    const [message, setMessage] = useState(null);

    const buttonStyle = {
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        margin: "10px",
        gap: "10px"
    }

    const labelStyle = {
        color: white,
        fontSize: "20px",
        textTransform: "uppercase"
    }

    const passwordLabelStyle = {
        color: dangerBackground,
        fontSize: "25px",
        textTransform: "uppercase",
        paddingTop: "30px"

    }

    async function handleSubmit(body) {
        setMessage(null);
        if (body.username === username) {
            body.username = "";
        }
        if (body.email === oldEmail) {
            body.email = "";
        }
        if (body.password === "") {
            setMessage("You must write your password to confirm the changes");
            return;
        }
        console.log(body);
        await fetchAuthenticatedWithBody(`/api/v1/users/${username}`, "PUT", body)
            .then(response => {
                if (response.status === 200) return response.json();
                else return Promise.reject("Something went wrong!");
            })
            .then(data => {
                tokenService.updateLocalAccessToken(data.token);
                tokenService.updateUsername(data.user.username);
                navigate(`/user/${body.username || username}/lastMatches`);
            })
            .catch(error => {
                const errorMessage = error.message || "An error occurred";
                setMessage(errorMessage);
            });

    }

    return (
        <div style={{display: "flex", flexDirection: "column", alignItems: "center", gap: "15px"}}>
            <Title style={{color: white, fontSize: '40px'}}>
                Edit Profile
            </Title>
            <form style={{display: 'flex', flexDirection: 'column', width: "600px", gap: "8px"}}
                  onSubmit={(e) => {
                      e.preventDefault();
                      handleSubmit({
                          username: e.target.username.value,
                          email: e.target.email.value,
                          password: e.target.password.value,
                          picture: e.target.picture.value
                      });
                  }}>
                <FormInput labelStyle={labelStyle}
                           label="Username"
                           name="username"
                           type="text"
                           placeholder="If you leave empty this field your name won`t change"
                           defaultValue={userData.username}/>
                <FormInput labelStyle={labelStyle}
                           label="Email"
                           name="email"
                           type="email"
                           placeholder="If you leave empty this field your email won`t change"
                           defaultValue={userData.email}/>
                <FormInput labelStyle={labelStyle}
                           label="Picture"
                           name={"picture"}
                           type="text"
                           placeholder={"Put the url of the image your user will have"}
                           defaultValue={userData.picture}/>

                <FormInput labelStyle={passwordLabelStyle}
                           label="To confirm the changes, write your password"
                           name={"password"}
                           type="password"
                           placeholder={"Write here your password to confirm the changes"}
                            textInputStyle={{backgroundColor: grayDarker}}/>

                {message && <Text style={{textTransform: "none", color: "red"}}>{message}</Text>}
                <div style={buttonStyle}>
                    <Button buttonType={ButtonType.primary} type="submit">Confirm Changes</Button>
                    <Button buttonType={ButtonType.danger} onClick={() => navigate(`/user/${username}/lastMatches`)}>Cancel</Button>
                </div>
            </form>
        </div>
    )
}
