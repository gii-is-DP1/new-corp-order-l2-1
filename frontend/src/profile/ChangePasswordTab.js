import {Title} from "../components/Title";
import {dangerBackground, gray, grayDarker, orange, white} from "../util/Colors";
import FormInput from "../components/FormInput";
import {Text} from "../components/Text";
import Button, {ButtonType} from "../components/Button";
import React from "react";
import fetchAuthenticatedWithBody from "../util/fetchAuthenticatedWithBody";
import {useState} from "react";
import tokenService from "../services/token.service";
import {PressableText} from "../components/PressableText";

export function ChangePasswordTab({username, oldEmail, userData, navigate}) {
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


    async function handleSubmit(body) {
        setMessage(null);
        await fetchAuthenticatedWithBody(`/api/v1/users/${username}/password`, "PUT", body)
            .then(response => {
                if (response.status === 200) return response.json();
                else return Promise.reject("Something went wrong!");
            })
            .then(data => {
                navigate(`/user/${username}/lastMatches`);
            })
            .catch(error => {
                const errorMessage = error.message || "An error occurred";
                setMessage(errorMessage);
            });

    }

    return (
        <div style={{display: "flex", flexDirection: "column", alignItems: "center", gap: "15px"}}>
            <Title style={{color: white, fontSize: '40px'}}>
                Change Password
            </Title>

            <form style={{display: 'flex', flexDirection: 'column', width: "600px", gap: "8px"}}
                  onSubmit={(e) => {
                      e.preventDefault();
                      handleSubmit({
                          oldPassword: e.target.oldPassword.value,
                          newPassword: e.target.newPassword.value
                      });
                  }}>
                <FormInput labelStyle={labelStyle}
                           label="Old Password"
                           name="oldPassword"
                           type="password"
                           placeholder="To change your password you must write your old password"/>
                <FormInput labelStyle={labelStyle}
                           label="New Password"
                           name="newPassword"
                           type="password"
                           placeholder="Write here your new password"/>


                    {message && <Text style={{textTransform: "none", color: "red"}}>{message}</Text>}
                <div style={buttonStyle}>
                    <Button buttonType={ButtonType.primary} type="submit">Confirm Changes</Button>
                    <Button buttonType={ButtonType.danger}
                            onClick={() => navigate(`/user/${username}/lastMatches`)}>Cancel</Button>
                </div>
            </form>
        </div>
)
}
