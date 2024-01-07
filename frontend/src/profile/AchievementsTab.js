import fetchAuthenticated from "../util/fetchAuthenticated";
import {buildErrorComponent, buildSuccessComponent} from "../util/formUtil";
import React, {useState} from "react";
import ListLine from "../components/ListLine";
import Button, {ButtonType} from "../components/Button";
import ProfilePicture from "../components/ProfilePicture";
import {Text} from "../components/Text";
import List from "../components/List";
import {black, grayDarker, orange, white} from "../util/Colors";
import TextInput from "../components/TextInput";
import AchievementPicture from "../components/AchievementPicture";
import {Subtitle} from "../components/Subtitle";
import * as Colors from "../util/Colors";
import {Title} from "../components/Title";

export function AchievementsTab({achievementsData, navigate, fetchAchievementData, isMe, rowListStyle}) {
    const [formMessage, setFormMessage] = useState(<></>)
    const [selectedAchievement, setSelectedAchievement] = useState(null)

    const listLineExpandedStyle = {
        backgroundColor: Colors.white,
        color: Colors.black,
        display: "flex",
        flexDirection: "row",
        borderRadius: "8px",
        height: "100px",
    }




    let achievementsItems = achievementsData?.map(achievement => {
        return <>{selectedAchievement === achievement.id ? (
            <ListLine style={listLineExpandedStyle} sideContent={
                <div style={{display: "flex", flexDirection: "column", gap: "5px", paddingRight: '30px'}}>
                    <Subtitle style={{color: orange, fontSize: '18px'}}> You need to have: </Subtitle>
                    <div style={{display: "flex", flexDirection: "row", gap: "10px"}}>
                        <Subtitle style={{fontSize: '25px', color: black}}> {achievement.threshold} </Subtitle>
                        <Subtitle style={{fontSize: '25px', color: grayDarker}}> {achievement.stat} </Subtitle>
                    </div>
                </div>
            }>
                <AchievementPicture url={achievement.imageUrl} style={{width: "80px", height: "80px"}} earned={false}/>
                <div style={{display: "flex", flexDirection: "column", gap: "10px"}}>
                    <Title style={{fontSize: '30px'}}> {achievement.name} </Title>
                    <Subtitle style={{fontSize: '20px', color: grayDarker}}> {achievement.description} </Subtitle>
                </div>
            </ListLine>
        ) : (
            <ListLine sideContent={
                <div style={{display: "flex", flexDirection: "row", gap: "5px"}}>
                    <Button buttonType={ButtonType.primary} onClick={() => setSelectedAchievement(achievement.id)}>
                        show Details
                    </Button>
                </div>
            }>
                <AchievementPicture url={achievement.imageUrl} style={{width: "40px", height: "40px"}} earned={false}/>
                <Subtitle> {achievement.name} </Subtitle>
                <Subtitle style={{fontSize: '12px', color: grayDarker}}> {achievement.description} </Subtitle>
            </ListLine>
        )}</>
    });

    return (
        <div style={{width: "100%", display: "flex", flexDirection: "column"}}>

            <List style={rowListStyle}>
                {achievementsItems}
            </List>

        </div>
    )
}
