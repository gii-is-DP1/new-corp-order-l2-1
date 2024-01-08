import fetchAuthenticated from "../../util/fetchAuthenticated";
import {buildErrorComponent, buildSuccessComponent} from "../../util/formUtil";
import React, {useEffect, useState} from "react";
import ListLine from "../../components/ListLine";
import Button, {ButtonType} from "../../components/Button";
import ProfilePicture from "../../components/ProfilePicture";
import {Text} from "../../components/Text";
import List from "../../components/List";
import {black, grayDarker, orange, white} from "../../util/Colors";
import TextInput from "../../components/TextInput";
import AchievementPicture from "../../components/AchievementPicture";
import {Subtitle} from "../../components/Subtitle";
import * as Colors from "../../util/Colors";
import {Title} from "../../components/Title";
import {AchievementExpandedView} from "./AchievementExpandedView";

export function AchievementsTab({achievementsCompleatedData, allAchievementsData, isMe, rowListStyle, username}) {
    const [selectedAchievement, setSelectedAchievement] = useState(null)
    const [notCompletedAchievementsData, setNotCompleatedAchievementsData] = useState([]);

    useEffect(() => {
        setNotCompleatedAchievementsData(allAchievementsData?.filter(achievement => {
            return !achievementsCompleatedData?.some(compleatedAchievement => {
                return compleatedAchievement.id === achievement.id
            })
        }))
    }, []);

    let compleatedAchievementsItems = achievementsCompleatedData?.map(achievement => {
        return <AchievementExpandedView achievement={achievement} setSelectedAchievement={setSelectedAchievement} selectedAchievement={selectedAchievement} earned={true}/>
    });


    let notCompleatedAchievementsItems = notCompletedAchievementsData?.map(achievement => {
        return <AchievementExpandedView achievement={achievement} setSelectedAchievement={setSelectedAchievement} selectedAchievement={selectedAchievement} earned={false}/>
    });

    return (
        <div style={{width: "100%", display: "flex", flexDirection: "column"}}>
            <List style={rowListStyle}>
                {compleatedAchievementsItems}
                {isMe && notCompleatedAchievementsItems}
            </List>

        </div>
    )
}
