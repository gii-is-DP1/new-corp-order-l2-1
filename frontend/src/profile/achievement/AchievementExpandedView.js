import ListLine from "../../components/ListLine";
import {Subtitle} from "../../components/Subtitle";
import {black, grayDarker, orange} from "../../util/Colors";
import AchievementPicture from "../../components/AchievementPicture";
import {Title} from "../../components/Title";
import Button, {ButtonType} from "../../components/Button";
import React from "react";
import * as Colors from "../../util/Colors";

export function AchievementExpandedView({achievement, selectedAchievement, setSelectedAchievement, earned, }){

    const listLineExpandedStyle = {
        backgroundColor: Colors.white,
        color: Colors.black,
        display: "flex",
        flexDirection: "row",
        borderRadius: "8px",
        height: "100px",
    }

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
            <AchievementPicture url={achievement.imageUrl} style={{width: "80px", height: "80px"}} earned={earned}/>
            <div style={{display: "flex", flexDirection: "column", gap: "10px"}}>
                <Title style={{fontSize: '30px'}}> {achievement.name} </Title>
                <Subtitle style={{fontSize: '20px', color: grayDarker}}> {achievement.description} </Subtitle>
            </div>
        </ListLine>
    ) : (
        <ListLine sideContent={
            <div style={{display: "flex", flexDirection: "row", gap: "5px"}}>
                <Button buttonType={ButtonType.secondaryLight} onClick={() => setSelectedAchievement(achievement.id)}>
                    show Details
                </Button>
            </div>
        }>
            <AchievementPicture url={achievement.imageUrl} style={{width: "40px", height: "40px"}} earned={earned}/>
            <Subtitle> {achievement.name} </Subtitle>
            <Subtitle style={{fontSize: '12px', color: grayDarker}}> {achievement.description} </Subtitle>
        </ListLine>
    )}</>
}
